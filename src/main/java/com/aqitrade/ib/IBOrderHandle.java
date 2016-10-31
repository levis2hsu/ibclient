package com.aqitrade.ib;

import com.ib.controller.ApiController.IOrderHandler;
import com.ib.controller.NewOrderState;
import com.ib.controller.OrderStatus;

public class IBOrderHandle implements IOrderHandler {

	IBResponse response;
	
	public IBOrderHandle(IBResponse orderResult){
		this.response=orderResult;
	}
	
	@Override
	public void orderState(NewOrderState orderState) {
		response.setOrderStatus(orderState.status());
		response.setErrorCode(0);
		
	}

	@Override
	public void orderStatus(OrderStatus status, int filled, int remaining,
			double avgFillPrice, long permId, int parentId,
			double lastFillPrice, int clientId, String whyHeld) {
		response.setOrderStatus(status);
		response.setErrorCode(0);
		
	}

	@Override
	public void handle(int errorCode, String errorMsg) {
		response.setErrorCode(errorCode);
		response.setMessage(errorMsg);

	}
	
	
	public IBResponse getOrderResult(){
		return response;
	}

}
