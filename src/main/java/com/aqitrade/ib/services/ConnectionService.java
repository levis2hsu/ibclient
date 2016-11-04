package com.aqitrade.ib.services;

import java.util.concurrent.ConcurrentHashMap;

import com.aqitrade.ib.IBApiController;

public class ConnectionService {
	
	private static ConcurrentHashMap<String, IBApiController> map = new ConcurrentHashMap<String, IBApiController>();
	
	public static IBApiController getIBApiController(String accountId) {
		IBApiController ibApiController = map.get(accountId);
		if (ibApiController==null){
			ibApiController=new IBApiController();
			map.put(accountId, ibApiController);
		}
		
		return ibApiController;
	}
	


}
