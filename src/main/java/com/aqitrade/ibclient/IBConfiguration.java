package com.aqitrade.ibclient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;

import com.aqitrade.ib.services.ConfigurationService;
import com.aqttrade.inclient.model.IBConnection;
import com.rabbitmq.client.ConnectionFactory;


public class IBConfiguration {
	
	private static IBConfiguration INSTANCE=new IBConfiguration();
	
	private IBConfiguration(){
		
	}
	
	public static IBConfiguration getINSTANCE(){
		return INSTANCE;
	}
	
	public final static String DEFAULT_IB_BROKER_ID="6000";
	
	private  String brokerId=null;
	
	private  String exchangeName=null;
	
	private  String exchangeType=null;
	
	private  String rabbitMQHost=null;
	
	private  int rabbitMQPort=5672;
	
	private  String rabbitMQUsername="guest";
	
	private  String rabbitMQPassword="guest";
	
	private  int exchangeThreadNum=20;
	
	private  Path connectionFile=null;
	

	public  ConcurrentHashMap<String, IBConnection> getConnections() {
		return connections;
	}

	
	private  ConcurrentHashMap<String, IBConnection>  connections=new ConcurrentHashMap<>();
	
	public  void addTwsConnection(String username, String host, String port){
		connections.put(username, new IBConnection(host, Integer.parseInt(port)));
	}
	
	public  IBConnection  getTwsConnection(String username){
		return connections.get(username);
	}
	
	public  void  deleteTwsConnection(String username){
		 connections.remove(username);
	}

	public  Path getConnectionFile(){
		return connectionFile;
	}
	
	public void loadConnections(Path connFile) throws IOException{
		
		Properties properties=new Properties();
		properties.load( this.getClass().getResourceAsStream("/ibclient.conf"));
		
		exchangeName=properties.getProperty("com.aqitrade.ibclient.messagebus.rabbitmq.exchange-name");
		exchangeType=properties.getProperty("com.aqitrade.ibclient.messagebus.rabbitmq.exchange-type");
		
		rabbitMQHost = properties.getProperty("com.aqitrade.ibclient.messagebus.rabbitmq.host");
		rabbitMQPort = Integer.parseInt( properties.getProperty("com.aqitrade.ibclient.messagebus.rabbitmq.port"));
		rabbitMQUsername = properties.getProperty("com.aqitrade.ibclient.messagebus.rabbitmq.username");
		rabbitMQPassword = properties.getProperty("com.aqitrade.ibclient.messagebus.rabbitmq.password");
		
		brokerId=properties.getProperty("broker.id");
		
		
		connectionFile=connFile;
		
		try (Stream<String> lines=Files.lines(connFile)){
		
			lines.forEach( (line) -> {
				String[] conn=line.split(",");
				addTwsConnection(conn[0].trim(), conn[1].trim(), conn[2].trim());
			});
		}
	}

	public  int getExchangePort() {
		return rabbitMQPort;
	}

	public  String getRabbitMQHost() {
		return rabbitMQHost;
	}

	public  String getExchangeUsername() {
		return rabbitMQUsername;
	}
	
	public  String getRabbitMQPassword() {
		return rabbitMQPassword;
	}
	
	public  String getBrokerId() {
		return brokerId;
	}

	public  String getExchangeName() {
		return exchangeName;
	}

	public  String getExchangeType() {
		return exchangeType;
	}

	public  int getExchangeThreadNum() {
		return exchangeThreadNum;
	}

	
}
