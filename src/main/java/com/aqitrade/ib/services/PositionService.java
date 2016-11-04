package com.aqitrade.ib.services;

import java.util.function.Consumer;

import com.aqitrade.ib.IBApiController;
import com.aqitrade.raduga.messagebus.messages.QueryPositionMessage;

public class PositionService {
	
	public static Consumer<QueryPositionMessage> queryPosition = (query) -> {
        String ibAccountId= query.getAccountAuthId();
        
        //get IBApiController by ibAccountId
        IBApiController  ibApiController=  ConnectionService.getIBApiController(ibAccountId);
        
        //query position info
    };
   
	
}
