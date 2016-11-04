package com.aqitrade.ib.services;

import java.util.function.Consumer;

import com.aqitrade.ib.IBApiController;
import com.aqitrade.raduga.messagebus.messages.CancelOrderMessage;
import com.aqitrade.raduga.messagebus.messages.OrderMessage;
import com.aqitrade.raduga.messagebus.messages.QueryOrderStatusMessage;

public class OrderService {
	
	
	public static Consumer<OrderMessage> order = (order) -> {
        String ibAccountId= order.getAccountAuthId();
        
        //get IBApiController by ibAccountId
        IBApiController  ibApiController=  ConnectionService.getIBApiController(ibAccountId);
        
        //place the order
    };
   
    
    public static Consumer<CancelOrderMessage> cancelOrder = (cancel) -> {
        String ibAccountId= cancel.getAccountAuthId();
        
        //get IBApiController by ibAccountId
        IBApiController  ibApiController=  ConnectionService.getIBApiController(ibAccountId);
        
        //cancel the order
    };
    
    
    public static Consumer<QueryOrderStatusMessage> queryOrderStatus = (query) -> {
        String ibAccountId= query.getAccountAuthID();
        
        //get IBApiController by ibAccountId
        IBApiController  ibApiController=  ConnectionService.getIBApiController(ibAccountId);
        
        //query the order
        
        
    };
    

}
