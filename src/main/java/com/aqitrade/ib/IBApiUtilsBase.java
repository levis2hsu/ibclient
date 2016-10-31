package com.aqitrade.ib;

import java.util.concurrent.ConcurrentHashMap;


public class IBApiUtilsBase {
	
	private static ConcurrentHashMap<String, IBApiController> map = new ConcurrentHashMap<String, IBApiController>();
	
	protected static IBApiController getIBApiController(String accountId) {
		IBApiController ibApiController = map.get(accountId);
		if (ibApiController==null){
			ibApiController=new IBApiController();
			map.put(accountId, ibApiController);
		}
		
		return ibApiController;
	}
	
	
	public static void removeApiController(String accountId){
		map.remove(accountId);
	}
	
	
	

}
