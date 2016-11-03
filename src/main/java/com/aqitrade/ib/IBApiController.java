package com.aqitrade.ib;

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
	
	
	public void placeOrder(NewContract contract, NewOrder order){
		if (order.orderId() == 0) {
			order.orderId( m_orderId++);
		}
		
		((IBApiClient)m_client).placeOrder(contract, order);
	}
	
	/*
	public void placeOrder(NewContract contract, NewOrder order){
//		m_client.placeOrder( contract, order);
		
		
		// not connected?
		if( !m_client.isConnected()) {
			m_client.notConnected();
			return;
		}

		// ApiController requires TWS 932 or higher; this limitation could be removed if needed
		if (m_client.serverVersion() < 66) {
			m_client.error( EClientErrors.NO_VALID_ID, EClientErrors.UPDATE_TWS, "ApiController requires TWS build 932 or higher to place orders.");
            return;
		}

		Builder b = new Builder();

		int VERSION = 41;

		// send place order msg
		try {
			b.send( m_client.PLACE_ORDER);
			b.send( VERSION);
			b.send( order.orderId() );
			b.send( contract.conid() );
			b.send( contract.symbol());
			b.send( contract.secType() );
			b.send( contract.expiry());
			b.send( contract.strike());
			b.send( contract.right().getApiString() );
			b.send( contract.multiplier() );
			b.send( contract.exchange() );
			b.send( contract.primaryExch() );
			b.send( contract.currency() );
			b.send( contract.localSymbol() );
            if (m_client.m_serverVersion >= m_client.MIN_SERVER_VER_TRADING_CLASS) {
                b.send(contract.tradingClass() );
            }
			b.send( contract.secIdType() );
			b.send( contract.secId() );
			b.send( order.action() );
			b.send( order.totalQuantity() );
			b.send( order.orderType() );
			b.send( order.lmtPrice() );
			b.send( order.auxPrice() );
			b.send( order.tif() );
			b.send( order.ocaGroup() );
			b.send( order.account() );
			b.send( ""); // open/close
			b.send( ""); // origin
			b.send( order.orderRef() );
			b.send( order.transmit() );
			b.send( order.parentId() );
			b.send( order.blockOrder() );
			b.send( order.sweepToFill() );
			b.send( order.displaySize() );
			b.send( order.triggerMethod() );
			b.send( order.outsideRth() );
			b.send( order.hidden() );

			// send combo legs for BAG orders
			if(contract.secType() == SecType.BAG) {
				b.send( contract.comboLegs().size());

				for (NewComboLeg leg : contract.comboLegs() ) {
					b.send( leg.conid() );
					b.send( leg.ratio() );
					b.send( leg.action().getApiString() );
					b.send( leg.exchange() );
					b.send( leg.openClose().getApiString() );
					b.send( leg.shortSaleSlot() );
					b.send( leg.designatedLocation() );
					b.send( leg.exemptCode() );
				}

				b.send( order.orderComboLegs().size());
				for (Double orderComboLeg : order.orderComboLegs() ) {
					b.send( orderComboLeg);
				}

				b.send( order.smartComboRoutingParams().size() );
				for (TagValue tagValue : order.smartComboRoutingParams() ) {
					b.send( tagValue.m_tag);
					b.send( tagValue.m_value);
				}
			}

			b.send( ""); // obsolete field
			b.send( order.discretionaryAmt() );
			b.send( order.goodAfterTime() );
			b.send( order.goodTillDate() );
			b.send( order.faGroup());
			b.send( order.faMethod() );
			b.send( order.faPercentage() );
			b.send( order.faProfile());
			b.send( 0); // short sale slot
			b.send( ""); // designatedLocation
			b.send( ""); // exemptCode
			b.send( order.ocaType() );
			b.send( order.rule80A() );
			b.send( ""); // settlingFirm
			b.send( order.allOrNone() );
			b.send( order.minQty() );
			b.send( order.percentOffset() );
			b.send( order.eTradeOnly() );
			b.send( order.firmQuoteOnly() );
			b.send( order.nbboPriceCap() );
			b.send( order.auctionStrategy() );
			b.send( order.startingPrice() );
			b.send( order.stockRefPrice() );
			b.send( order.delta() );
			b.send( order.stockRangeLower() );
			b.send( order.stockRangeUpper() );
			b.send( order.overridePercentageConstraints() );
			b.send( order.volatility() );
			b.send( order.volatilityType() );
			b.send( order.deltaNeutralOrderType() );
			b.send( order.deltaNeutralAuxPrice() );

			if (order.deltaNeutralOrderType() != OrderType.None) {
				b.send( order.deltaNeutralConId() );
				b.send( ""); //deltaNeutralSettlingFirm
				b.send( ""); //deltaNeutralClearingAccount
				b.send( ""); //deltaNeutralClearingIntent
				b.send( ""); //deltaNeutralOpenClose
                b.send( ""); //deltaNeutralShortSale
                b.send( ""); //deltaNeutralShortSaleSlot
                b.send( ""); //deltaNeutralDesignatedLocation
			}
			
			b.send( order.continuousUpdate() );
			b.send( order.referencePriceType() );
			b.send( order.trailStopPrice() );
			b.send( order.trailingPercent() );
			b.send( order.scaleInitLevelSize() );
			b.send( order.scaleSubsLevelSize() );
			b.send( order.scalePriceIncrement() );

			if (order.scalePriceIncrement() != 0 && order.scalePriceIncrement() != Double.MAX_VALUE) {
				b.send( order.scalePriceAdjustValue() );
				b.send( order.scalePriceAdjustInterval() );
				b.send( order.scaleProfitOffset() );
				b.send( order.scaleAutoReset() );
				b.send( order.scaleInitPosition() );
				b.send( order.scaleInitFillQty() );
				b.send( order.scaleRandomPercent() );
			}

			if (m_client.m_serverVersion >= m_client.MIN_SERVER_VER_SCALE_TABLE) {
				b.send( order.scaleTable() );
				b.send( ""); // active start time
				b.send( ""); // active stop time
			}

	        b.send( order.hedgeType() );
			if (order.hedgeType() != HedgeType.None) {
				b.send( order.hedgeParam() );
			}

			b.send( order.optOutSmartRouting() );
			b.send( "");//clearingAccount
			b.send( "");//clearingIntent
			b.send( order.notHeld() );

			b.send( contract.underComp() != null);
			if (contract.underComp() != null) {
				b.send( contract.underComp().conid() );
				b.send( contract.underComp().delta() );
				b.send( contract.underComp().price() );
			}

			b.send( order.algoStrategy() );
			if( order.algoStrategy() != AlgoStrategy.None) {
				b.send( order.algoParams().size() );
				for( TagValue tagValue : order.algoParams() ) {
					b.send( tagValue.m_tag);
					b.send( tagValue.m_value);
				}
			}

			b.send( order.whatIf() );

			m_client.m_dos.write( b.getBytes() );
		}
		catch( Exception e) {
			e.printStackTrace();
			error( order.orderId(), 512, "Order sending error - " + e);
			m_client.close();
		}
		
		
	}
	*/
	

}
