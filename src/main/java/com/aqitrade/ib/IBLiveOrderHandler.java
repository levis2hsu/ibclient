package com.aqitrade.ib;

import com.ib.controller.ApiController.ILiveOrderHandler;
import com.ib.controller.NewContract;
import com.ib.controller.NewOrder;
import com.ib.controller.NewOrderState;
import com.ib.controller.OrderStatus;

public class IBLiveOrderHandler implements ILiveOrderHandler{

	@Override
	public void openOrder(NewContract contract, NewOrder order,
			NewOrderState orderState) {
		System.out.println("contract:"+ contract.toString() + ",order:" + order +
				"orderState:" + orderState);
		
	}

	@Override
	public void openOrderEnd() {
		System.out.println("openOrderEnd");
		
	}

	@Override
	public void orderStatus(int orderId, OrderStatus status, int filled,
			int remaining, double avgFillPrice, long permId, int parentId,
			double lastFillPrice, int clientId, String whyHeld) {
		System.out.println("orderStatus:" + "orderId"+ orderId);
		
		
	}

	@Override
	public void handle(int orderId, int errorCode, String errorMsg) {
		System.out.println("handle:"+"orderId"+ orderId);
		
	}

}
