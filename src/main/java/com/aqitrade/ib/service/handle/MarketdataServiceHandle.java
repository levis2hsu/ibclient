package com.aqitrade.ib.service.handle;

import org.springframework.beans.factory.annotation.Autowired;

import com.aqitrade.ib.services.messagebus.Encoders;
import com.aqitrade.ib.services.messagebus.MessagebusService;
import com.aqitrade.raduga.messagebus.messages.InstrumentMessage;

public class MarketdataServiceHandle {
	
	@Autowired
	MessagebusService messagebusService;
	
	public void produce(InstrumentMessage instrumentMessage){
		messagebusService.produce(instrumentMessage, Encoders.instrumentMessage);
	}

}
