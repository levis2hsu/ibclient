package com.aqitrade.ib;

import com.ib.controller.OrderStatus;

public class IBResponse {
	
	
	private int errorCode;
	private String message;
	private OrderStatus orderStatus;
	
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public String getMessage() {
		return message;
	}
	
	
	public IBResponse(){
		
	}

	public IBResponse(OrderStatus orderStatus, int errorCode, String message){
		this.orderStatus=orderStatus;
		this.errorCode=errorCode;
		this.message=message;
	}

}
