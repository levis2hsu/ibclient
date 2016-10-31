package com.aqitrade.ib;


public class IBApiUtils extends IBApiUtilsBase{

		
	public static void connect(String accountId, String host, int port, int clientId){
		
		IBApiController ibApiController = getIBApiController(accountId);
		ibApiController.connect(host, port, clientId);
	}
	
	public static void disconnect(String accountId) {
		
		IBApiController ibApiController = getIBApiController(accountId);
		ibApiController.disconnect();
	}
	
}
