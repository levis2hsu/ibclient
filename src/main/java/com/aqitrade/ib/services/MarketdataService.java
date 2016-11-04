package com.aqitrade.ib.services;

import java.util.function.Consumer;

import com.aqitrade.raduga.messagebus.messages.SubscribeMarketDataMessage;
import com.aqitrade.raduga.messagebus.messages.UnsubscribeMarketDataMessage;

public class MarketdataService {
	
	
	public static Consumer<SubscribeMarketDataMessage> subcribeMarket = (sub) -> {
		// missing account info now
		
//        String ibAccountId= order.instrumentID;
//        
//        //get IBApiController by ibAccountId
//        IBApiController  ibApiController=  ConnectionService.getIBApiController(ibAccountId);
//        
        //place the order
    };
   
    
    public static Consumer<UnsubscribeMarketDataMessage> unsubcribeMarket = (unsub) -> {
    		
    };		
 
}
