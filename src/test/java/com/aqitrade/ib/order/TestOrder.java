package com.aqitrade.ib.order;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.aqitrade.ib.IBApiController;
import com.ib.controller.NewContract;
import com.ib.controller.NewOrder;
import com.ib.controller.OrderType;
import com.ib.controller.Types.Action;
import com.ib.controller.Types.Right;
import com.ib.controller.Types.SecType;
import com.ib.controller.Types.TimeInForce;

public class TestOrder {

	private static IBApiController ibApi;
	final static private String host = "localhost";
	final static private int port = 7496;
	final static private int clientId = 0;
	
	private int newOrderId = 0;
	
	@BeforeClass
	public static void init(){
		ibApi = new IBApiController();
		ibApi.connect(host, port, clientId);
	}
	
	@Test
	public void testOrder(){
		placeOrder();
		modifyOrder();
		cancelOrder();
	}
	
//	@Test
	public void placeOrder(){
		
		NewContract contract = new NewContract();
		
		contract.symbol("IBM"); 
		contract.secType(SecType.STK); 
		contract.expiry(""); 
		contract.strike(0l); 
		contract.right(Right.None); 
		contract.multiplier(""); 
		contract.exchange( "SMART");
		contract.primaryExch("ISLAND");
		contract.currency( "USD" ); 
		contract.localSymbol("");
		contract.tradingClass("");
		
		
		
		NewOrder order = new NewOrder();
		
		//get account id
		String account = ibApi.getAccounts()[0];
		
		order.account(account);
		order.action(Action.BUY);
		order.totalQuantity(200);
		order.displaySize(0);
		order.orderType(OrderType.LMT );
		order.lmtPrice( 1.0);
		order.auxPrice();
		order.tif( TimeInForce.DAY);
		
		//place the order 
		ibApi.placeOrModifyOrder(contract, order);
		newOrderId = order.orderId();
		
	}
	
//	@Test
	public void modifyOrder(){
		
		if( newOrderId ==0 )
			return;
		
		NewContract contract = new NewContract();
		contract.symbol("IBM"); 
		contract.secType(SecType.STK); 
		contract.expiry(""); 
		contract.strike(0l); 
		contract.right(Right.None); 
		contract.multiplier(""); 
		contract.exchange( "SMART");
		contract.primaryExch("ISLAND");
		contract.currency( "USD" ); 
		contract.localSymbol("");
		contract.tradingClass("");
		
		NewOrder order = new NewOrder();
		order.orderId(newOrderId);
		
		//get account id
		String account = ibApi.getAccounts()[0];
		
		order.account(account);
		order.action(Action.BUY);
		
		//in place order, it's 200, modify to 100
		order.totalQuantity(100);
		order.displaySize(0);
		order.orderType(OrderType.LMT );
		order.lmtPrice( 1.0);
		order.auxPrice();
		order.tif( TimeInForce.DAY);
		
		//modify the order
		ibApi.placeOrModifyOrder(contract, order);
		
	}
	
//	@Test
	public void cancelOrder(){
		if(newOrderId != 0){
			ibApi.cancelOrder(newOrderId);
		}
	}
	
	@AfterClass
	public static void terminate(){
		ibApi.disconnect();
	}
	
	
}
