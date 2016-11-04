package com.aqitrade.ib.services;

import java.util.function.Consumer;

import com.aqitrade.ib.IBApiController;
import com.aqitrade.raduga.messagebus.messages.QueryAccountMessage;
import com.aqitrade.raduga.messagebus.messages.QueryCashMessage;

public class AccountService {

	public static Consumer<QueryAccountMessage> queryAccount = (order) -> {
        String ibAccountId= order.getAccountAuthId();
        
        //get IBApiController by ibAccountId
        IBApiController  ibApiController=  ConnectionService.getIBApiController(ibAccountId);
        
        //query account info
    };
   
    
    public static Consumer<QueryCashMessage> queryCash = (order) -> {
        String ibAccountId= order.getAccountAuthId();
        
        //get IBApiController by ibAccountId
        IBApiController  ibApiController=  ConnectionService.getIBApiController(ibAccountId);
        
        //query cash info
    };
    
	
}
