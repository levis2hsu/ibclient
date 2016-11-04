package com.aqitrade.ib.service.handle;

import org.springframework.beans.factory.annotation.Autowired;

import com.aqitrade.ib.services.messagebus.Encoders;
import com.aqitrade.ib.services.messagebus.MessagebusService;
import com.aqitrade.raduga.messagebus.messages.OrderStatusMessage;


/**
 * This handle push the order response message back to the message bus
 * @author Levis
 *
 */
public class OrderServiceHandle {
	
	@Autowired
	MessagebusService messagebusService;
		
	public void produce(OrderStatusMessage orderStatusMessage){
			
		messagebusService.produce(orderStatusMessage, Encoders.orderStatusMessage);
	}

}
