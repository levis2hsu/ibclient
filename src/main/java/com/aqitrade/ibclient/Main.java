package com.aqitrade.ibclient;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.stereotype.Component;

import akka.actor.ActorSystem;

import com.aqitrade.ib.services.messagebus.Encoders;
import com.aqitrade.ib.services.messagebus.MessagebusService;
import com.aqitrade.ib.services.messagebus.impl.rabbitmq.Decoders;
import com.aqitrade.raduga.messagebus.messages.OrderDirection;
import com.aqitrade.raduga.messagebus.messages.OrderMessage;

@Component
public class Main {

    @Autowired
    MessagebusService messagebusService;

    @Autowired
    ActorSystem actorSystem;

    private void run() throws IOException {


        messagebusService.subscribe(OrderMessage.class, m -> System.out.println(m), Decoders.orderMessage);

        System.in.read();

        OrderMessage order = new OrderMessage(123L, "", 234L, "", "", "", "Symbol", OrderDirection.order_direction_long, Collections.EMPTY_LIST, 0.0, 1, "", "");

        messagebusService.produce(order, Encoders.orderMessage);

        actorSystem.shutdown();

    }

    public static void main(String[] args) throws IOException {

        SimpleCommandLinePropertySource ps = new SimpleCommandLinePropertySource(args);

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().getPropertySources().addFirst(ps);
        ctx.scan(Main.class.getPackage().getName());
        ctx.register(AppConfig.class);
        ctx.refresh();

        final Main main = ctx.getBean(Main.class);

        main.run();

        ctx.destroy();

    }

}
