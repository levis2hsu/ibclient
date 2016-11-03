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
	
	@BeforeClass
	public static void init(){
		ibApi = new IBApiController();
		ibApi.connect(host, port, clientId);
	}
	
	@Test
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
		
		String account = ibApi.getAccounts()[0];
		
		order.account(account);
		order.action(Action.BUY);
		order.totalQuantity(200);
		order.displaySize(0);
		order.orderType(OrderType.LMT );
		order.lmtPrice( 1.0);
		order.auxPrice();
		order.tif( TimeInForce.DAY);
//		if (contract.isCombo() ) {
//			TagValue tv = new TagValue( ComboParam.NonGuaranteed.toString(), m_nonGuaranteed.isSelected() ? "1" : "0");
//			order.smartComboRoutingParams().add( tv);
//		}
		
		//place the order 
		ibApi.placeOrder(contract, order);
		
	}
	
	@AfterClass
	public static void terminate(){
		ibApi.disconnect();
	}
	
	
}
