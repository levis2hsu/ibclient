package com.aqitrade.ib.service.handle;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import com.aqitrade.ib.services.messagebus.Encoders;
import com.aqitrade.ib.services.messagebus.MessagebusService;
import com.aqitrade.ibclient.IBConfiguration;
import com.aqitrade.raduga.messagebus.messages.OrderStatusMessage;
import com.ib.controller.ApiController.ILiveOrderHandler;
import com.ib.controller.NewContract;
import com.ib.controller.NewOrder;
import com.ib.controller.NewOrderState;
import com.ib.controller.OrderStatus;


/**
 * This handle push the order response message back to the message bus
 * @author Levis
 *
 */
public class OrderServiceHandle implements ILiveOrderHandler{
	
	@Autowired
	MessagebusService messagebusService;
		
	private void produce(OrderStatusMessage orderStatusMessage){
			
		messagebusService.produce(orderStatusMessage, Encoders.orderStatusMessage);
	}

	@Override
	public void openOrder(NewContract contract, NewOrder order,
			NewOrderState orderState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void openOrderEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void orderStatus(int orderId, OrderStatus status, int filled,
			int remaining, double avgFillPrice, long permId, int parentId,
			double lastFillPrice, int clientId, String whyHeld) {
		
		OrderStatusMessage orderStatusMessage=new OrderStatusMessage();
		orderStatusMessage.setOrderID((long)orderId);
		
		//if status is null, not need to update raguda
		orderStatusMessage.setStatus(convertRadugaOrderStatus(status));
		if (orderStatusMessage.getStatus()==null)  return;
		
		orderStatusMessage.setQty(filled);
		
		orderStatusMessage.setExchangeOrderID(IBConfiguration.getINSTANCE().getBrokerId());

		orderStatusMessage.setCreateDate(LocalDateTime.now().toString() );
		
		produce(orderStatusMessage);
		
	}

	@Override
	public void handle(int orderId, int errorCode, String errorMsg) {
		// TODO Auto-generated method stub
		
	}
	
	
	/**
	 * may need to add more status 
	 * @param ibOrderStatus
	 * @return
	 */
	
	private  com.aqitrade.raduga.messagebus.messages.OrderStatus  convertRadugaOrderStatus(OrderStatus ibOrderStatus){
		
		switch(ibOrderStatus){
			case ApiPending:
				return 	null;
			case ApiCancelled:
				return 	com.aqitrade.raduga.messagebus.messages.OrderStatus.order_status_cancelled;
			case PreSubmitted:
				return 	null;
			case PendingCancel:
				return 	null;
			case Cancelled:
				return 	com.aqitrade.raduga.messagebus.messages.OrderStatus.order_status_cancelled;
			case Submitted:
				return 	com.aqitrade.raduga.messagebus.messages.OrderStatus.order_status_new;
			case Filled:
				return 	com.aqitrade.raduga.messagebus.messages.OrderStatus.order_status_executed;
			case PendingSubmit:
				return 	null;
			case Inactive:
				return 	null;
			case Unknown:
				return 	com.aqitrade.raduga.messagebus.messages.OrderStatus.order_status_rejected;	
				
			default:
				return null;
		}
	}
	

}
