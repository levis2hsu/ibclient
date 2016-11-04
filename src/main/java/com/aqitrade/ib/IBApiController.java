package com.aqitrade.ib;

import com.aqitrade.ib.service.handle.MarketdataServiceHandle;
import com.aqitrade.ib.service.handle.OrderServiceHandle;
import com.ib.client.CommissionReport;
import com.ib.client.Contract;
import com.ib.client.ContractDetails;
import com.ib.client.EClientSocket;
import com.ib.client.EWrapper;
import com.ib.client.Execution;
import com.ib.client.Order;
import com.ib.client.OrderState;
import com.ib.client.UnderComp;
import com.ib.controller.NewContract;
import com.ib.controller.NewOrder;


public class IBApiController implements EWrapper{
	
	private EClientSocket m_client;
	
	private String[] m_accounts; 
	
	private int m_respId;
	
	private int m_errorCode;
	
	private String m_errorMsg;
	
	private String m_errorString;
	
	private Exception m_exception;
	
	// used for all requests except orders; designed not to conflict with m_orderId
	private int m_reqId;	
	private int m_orderId;

	
	private OrderServiceHandle  orderServiceHandle=new OrderServiceHandle(); 
	
	private MarketdataServiceHandle  marketDataServiceHandle=new MarketdataServiceHandle(); 
	
	
		
	public IBApiController(){
		m_client=new IBApiClient(this);
	}
	
	
	public void connect( String host, int port, int clientId) {
		m_client.eConnect(host, port, clientId);
	}

	public void disconnect() {
		m_client.eDisconnect();
	}

	public String[] getAccounts(){
		return m_accounts;
	}
	
	@Override
	public void managedAccounts(String accountsList) {
		m_accounts=accountsList.split(",");
	}

	
	@Override
	public void error(Exception e) {
		this.m_exception=e;
	}


	@Override
	public void error(String str) {
		this.m_errorString=str;
	}


	@Override
	public void error(int id, int errorCode, String errorMsg) {
		this.m_respId=id;
		this.m_errorCode=errorCode;
		this.m_errorMsg=errorMsg;
	}


	@Override
	public void connectionClosed() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void tickPrice(int tickerId, int field, double price,
			int canAutoExecute) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void tickSize(int tickerId, int field, int size) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void tickOptionComputation(int tickerId, int field,
			double impliedVol, double delta, double optPrice,
			double pvDividend, double gamma, double vega, double theta,
			double undPrice) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void tickGeneric(int tickerId, int tickType, double value) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void tickString(int tickerId, int tickType, String value) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void tickEFP(int tickerId, int tickType, double basisPoints,
			String formattedBasisPoints, double impliedFuture, int holdDays,
			String futureExpiry, double dividendImpact, double dividendsToExpiry) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void orderStatus(int orderId, String status, int filled,
			int remaining, double avgFillPrice, int permId, int parentId,
			double lastFillPrice, int clientId, String whyHeld) {
		// TODO Auto-generated method stub
		System.out.println( "order :" + orderId + ",  status: " + status);
	}


	@Override
	public void openOrder(int orderId, Contract contract, Order order,
			OrderState orderState) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void openOrderEnd() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateAccountValue(String key, String value, String currency,
			String accountName) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updatePortfolio(Contract contract, int position,
			double marketPrice, double marketValue, double averageCost,
			double unrealizedPNL, double realizedPNL, String accountName) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateAccountTime(String timeStamp) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void accountDownloadEnd(String accountName) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void nextValidId(int orderId) {
		// TODO Auto-generated method stub
		m_orderId = orderId;
		m_reqId = m_orderId + 10000000; // let order id's not collide with other request id's
	}


	@Override
	public void contractDetails(int reqId, ContractDetails contractDetails) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void bondContractDetails(int reqId, ContractDetails contractDetails) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void contractDetailsEnd(int reqId) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void execDetails(int reqId, Contract contract, Execution execution) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void execDetailsEnd(int reqId) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateMktDepth(int tickerId, int position, int operation,
			int side, double price, int size) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateMktDepthL2(int tickerId, int position,
			String marketMaker, int operation, int side, double price, int size) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateNewsBulletin(int msgId, int msgType, String message,
			String origExchange) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void receiveFA(int faDataType, String xml) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void historicalData(int reqId, String date, double open,
			double high, double low, double close, int volume, int count,
			double WAP, boolean hasGaps) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void scannerParameters(String xml) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void scannerData(int reqId, int rank,
			ContractDetails contractDetails, String distance, String benchmark,
			String projection, String legsStr) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void scannerDataEnd(int reqId) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void realtimeBar(int reqId, long time, double open, double high,
			double low, double close, long volume, double wap, int count) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void currentTime(long time) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void fundamentalData(int reqId, String data) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void deltaNeutralValidation(int reqId, UnderComp underComp) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void tickSnapshotEnd(int reqId) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void marketDataType(int reqId, int marketDataType) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void commissionReport(CommissionReport commissionReport) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void position(String account, Contract contract, int pos,
			double avgCost) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void positionEnd() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void accountSummary(int reqId, String account, String tag,
			String value, String currency) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void accountSummaryEnd(int reqId) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void verifyMessageAPI(String apiData) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void verifyCompleted(boolean isSuccessful, String errorText) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void displayGroupList(int reqId, String groups) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void displayGroupUpdated(int reqId, String contractInfo) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void placeOrModifyOrder(NewContract contract, NewOrder order){
		if (order.orderId() == 0) {
			order.orderId( m_orderId++);
		}
		
		((IBApiClient)m_client).placeOrder(contract, order);
	}
	
	public void cancelOrder(int orderId){
		m_client.cancelOrder(orderId);
	}

}
