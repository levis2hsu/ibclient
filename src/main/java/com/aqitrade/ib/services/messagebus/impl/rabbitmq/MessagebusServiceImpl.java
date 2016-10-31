package com.aqitrade.ib.services.messagebus.impl.rabbitmq;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.UntypedActor;

import com.aqitrade.ib.services.ConfigurationService;
import com.aqitrade.ib.services.messagebus.MessagebusService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

@Service
public class MessagebusServiceImpl extends MessagebusService {

    @Autowired
    ConfigurationService config;

    @Autowired
    ActorSystem actorSystem;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ExecutorService subscribersExecutor = Executors.newFixedThreadPool(20);

    private ConnectionFactory connectionFactory;

    private Connection connection;

    private HashMap<String, Subscriber> subscribers = new HashMap<>();

    @PostConstruct
    private void init(){
        subscribersExecutor = Executors.newFixedThreadPool(Math.min(300, Math.max(0, config.getIntOrFail("com.aqitrade.hsbc.messagebus.connector-threads"))));
        connectionFactory = connectionFactory();
    }

    @PreDestroy
    private void terminate(){
        for(Subscriber subscriber : subscribers.values()){
            subscriber.terminateConsumers();
        }
        try {
            connection.close();
        } catch (IOException e) {
            logger.warn("Could not close connection to messagebus.");
        }
        subscribersExecutor.shutdown();
    }

    @Override
    public <T> void subscribe(Class<T> messageType, Consumer<T> callback, Function<byte[], T> decoder, String consumerGroup) {
        String topic = composeTopicName(messageType);

        subscribers.putIfAbsent(topic, newSubscriber(topic));

        subscribers.get(topic).addConsumer(
                actorSystem.actorOf(Subscription.props(callback, decoder))
        );

    }

    @Override
    public <T> T produce(T message, Function<T, byte[]> encoder){

        String topic = composeTopicName(message.getClass());

        Channel channel = channel();

        try {

            String exchangeName = config.getStringOrFail("com.aqitrade.hsbc.messagebus.rabbitmq.exchange-name");

            String exchangeType = config.getStringOrFail("com.aqitrade.hsbc.messagebus.rabbitmq.exchange-type");

            try {

                channel.exchangeDeclare(exchangeName, exchangeType);
                channel.basicPublish(exchangeName, topic, null, encoder.apply(message));

            } catch (IOException e) {
                logger.warn("Could not deliver a message to topic [" + topic + "].");
            }

        } finally {

            try {
                channel.close();
            } catch (Exception e) {
                logger.warn("Could not close channel after sending a message to topic [" + topic + "].");
            }

        }
        return message;
    }

    private Subscriber newSubscriber(String topic){
        Subscriber subscriber = new Subscriber(topic);
        subscribersExecutor.submit(subscriber.subscribe);
        return subscriber;
    }

    private ConnectionFactory connectionFactory() {

        String host = config.getStringOrFail("com.aqitrade.hsbc.messagebus.rabbitmq.host");
        int port = config.getInt("com.aqitrade.hsbc.messagebus.rabbitmq.port").orElse(ConnectionFactory.DEFAULT_AMQP_PORT);
        String username = config.getString("com.aqitrade.hsbc.messagebus.rabbitmq.username").orElse(ConnectionFactory.DEFAULT_USER);
        String password = config.getString("com.aqitrade.hsbc.messagebus.rabbitmq.password").orElse(ConnectionFactory.DEFAULT_PASS);

        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);

        return factory;

    }

    private Channel channel() {

        Channel channel = null;

        while(channel == null) {

            try {

                while (connection == null || !connection.isOpen()) {
                    connection = connectionFactory.newConnection();
                }

                channel = connection.createChannel();

            } catch (Exception e) {
                logger.warn("Could not connect to the messagebus", e);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ie) {
                    logger.debug("Interrupted", ie);
                }
            }
        }

        return channel;

    }

    private <T> String composeTopicName(Class<T> messageType) {
        return messageType.getCanonicalName();
    }

    private static class Subscription<T> extends UntypedActor {

        private Consumer<T> callback;
        private Function<byte[], T> decoder;

        Subscription(Consumer<T> callback, Function<byte[], T> decoder) {
            this.callback = callback;
            this.decoder = decoder;
        }

        public static <T> Props props(Consumer<T> callback, Function<byte[], T> decoder) {
            return Props.create(Subscription.class, callback, decoder);
        }

        @Override
        public void onReceive(Object message) throws Throwable {
            if (message instanceof byte[]) {
                callback.accept(decoder.apply((byte[])message));
            }
        }
    }

    private class Subscriber{
        private String topic;
        private LinkedList<ActorRef> consumers = new LinkedList<>();

        Subscriber(String topic){
            this.topic = topic;
        }

        void addConsumer(ActorRef consumer) {
            consumers.add(consumer);
        }

        void terminateConsumers(){
            sendAllConsumers(PoisonPill.getInstance());
        }

        private void sendAllConsumers(Object message){
            for(ActorRef consumer : consumers){
                consumer.tell(message, ActorRef.noSender());
            }
        }

        Callable<Void> subscribe = () -> {

            String exchangeName = config.getStringOrFail("com.aqitrade.hsbc.messagebus.rabbitmq.exchange-name");

            String exchangeType = config.getStringOrFail("com.aqitrade.hsbc.messagebus.rabbitmq.exchange-type");

            Channel channel = channel();

            try {
                channel.exchangeDeclare(exchangeName, exchangeType);

                String queueName = channel.queueDeclare().getQueue();

                channel.queueBind(queueName, exchangeName, topic);

                QueueingConsumer consumer = new QueueingConsumer(channel);

                channel.basicConsume(queueName, true, consumer);

                while (true) {

                    QueueingConsumer.Delivery delivery = null;
                    try {
                        delivery = consumer.nextDelivery();

                        byte[] body = delivery.getBody();

                        sendAllConsumers(body);

                    } catch (InterruptedException e) {
                        logger.debug("Interrupted", e);
                    }

                }

            } catch (IOException e) {
                logger.warn("Could not connect to the messagebus", e);
            } finally {
                try {
                    channel.close();
                } catch (Throwable e) {
                    logger.warn("Could not close subscription to topic [" + topic + "].");
                }
            }

            return null;

        };
    }

}
