package com.aqitrade.ibclient;

import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import akka.actor.ActorSystem;

import com.aqitrade.ib.services.ConfigurationService;
import com.aqitrade.ib.services.OrderService;
import com.aqitrade.ib.services.messagebus.MessagebusService;
import com.aqitrade.ib.services.messagebus.impl.Decoders;
import com.aqitrade.ib.services.messagebus.impl.MessagebusServiceImpl;
import com.aqitrade.raduga.messagebus.messages.CancelOrderMessage;
import com.aqitrade.raduga.messagebus.messages.OrderMessage;
import com.aqitrade.raduga.messagebus.messages.QueryOrderStatusMessage;

@Component
public class Main {
	
    @Autowired
    MessagebusService messagebusService;

    @Autowired
    ActorSystem actorSystem;

    private void run() throws IOException {

    	//start monitor service for the IBConnectionConfiguration properties file change
    	
    	//subscribe order messages
        messagebusService.subscribe(OrderMessage.class, OrderService.order, Decoders.orderMessage);
        messagebusService.subscribe(CancelOrderMessage.class, OrderService.cancelOrder, Decoders.cancelOrderMessage);
        messagebusService.subscribe(QueryOrderStatusMessage.class, OrderService.queryOrderStatus, Decoders.queryOrderStatusMessage);

        //subscribe  market data
        
        
        //loop for the message from raduga
        while (true){
        	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
    }

    public static void main(String[] args) throws IOException {

    	if (args.length!=1){
    		System.out.println("Connection property file is not defined");
    		System.out.println("Usage: java Main connecton.property");
    		System.exit(-1);
    	}
    	
        IBConfiguration.getINSTANCE().loadConnections(Paths.get(args[0]));
  	  
        try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext()){;
	        ctx.scan(Main.class.getPackage().getName());
	        ctx.scan(MessagebusServiceImpl.class.getPackage().getName());
	        ctx.scan(ConfigurationService.class.getPackage().getName());
	        
	        ctx.register(AppConfig.class);
	        
	        ctx.refresh();
	        
	    	
	        final Main main = ctx.getBean(Main.class);
	
	        main.run();
        }
    }

}
