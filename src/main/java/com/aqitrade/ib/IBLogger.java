package com.aqitrade.ib;

import com.ib.controller.ApiConnection.ILogger;

public class IBLogger implements ILogger{

	@Override
	public void log(String valueOf) {
		System.out.println(valueOf);
		
	}

}
