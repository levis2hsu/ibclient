package com.aqitrade.ibclient;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.stereotype.Component;

import akka.actor.ActorSystem;

import com.aqitrade.ib.services.ConfigurationService;
import com.aqitrade.ib.services.OrderService;
import com.aqitrade.ib.services.messagebus.Encoders;
import com.aqitrade.ib.services.messagebus.MessagebusService;
import com.aqitrade.ib.services.messagebus.impl.rabbitmq.Decoders;
import com.aqitrade.ib.services.messagebus.impl.rabbitmq.MessagebusServiceImpl;
import com.aqitrade.raduga.messagebus.messages.CancelOrderMessage;
import com.aqitrade.raduga.messagebus.messages.OrderDirection;
import com.aqitrade.raduga.messagebus.messages.OrderMessage;
import com.aqitrade.raduga.messagebus.messages.QueryOrderStatusMessage;

@Component
public class Main {

    @Autowired
    MessagebusService messagebusService;

    @Autowired
    ActorSystem actorSystem;
    
    
    private static String connectionFile;
    

    private void run() throws IOException {

    	//start monitor service for the properties file change
    	
    	
    	//subscribe order messages
        messagebusService.subscribe(OrderMessage.class, OrderService.order, Decoders.orderMessage);
        messagebusService.subscribe(CancelOrderMessage.class, OrderService.cancelOrder, Decoders.cancelOrderMessage);
        messagebusService.subscribe(QueryOrderStatusMessage.class, OrderService.queryOrderStatus, Decoders.queryOrderStatusMessage);

        //subscribe  market data
        
        
        
        
        
        OrderMessage order = new OrderMessage(123L, "", 234L, "", "", "", "Symbol", OrderDirection.order_direction_long, Collections.EMPTY_LIST, 0.0, 1, "", "");

        messagebusService.produce(order, Encoders.orderMessage);

    }

    public static void main(String[] args) throws IOException {

    	if (args.length!=2){
    		System.out.println("Connection property file is not defined");
    		System.out.println("Usage: java Main connecton.property");
    		System.exit(-1);
    	}
    	
    	connectionFile=args[1];
    	
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.scan(Main.class.getPackage().getName());
        ctx.scan(MessagebusServiceImpl.class.getPackage().getName());
        ctx.scan(ConfigurationService.class.getPackage().getName());
        
        ctx.register(AppConfig.class);
        
        ctx.refresh();

        final Main main = ctx.getBean(Main.class);

        main.run();

        ctx.destroy();

    }

}
