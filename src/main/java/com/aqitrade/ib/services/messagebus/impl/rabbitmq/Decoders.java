package com.aqitrade.ib.services.messagebus.impl.rabbitmq;

import java.io.IOException;
import java.util.function.Function;

import org.apache.avro.generic.GenericContainer;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;

import com.aqitrade.raduga.messagebus.messages.CancelOrderMessage;
import com.aqitrade.raduga.messagebus.messages.OrderMessage;
import com.aqitrade.raduga.messagebus.messages.OrderStatusMessage;
import com.aqitrade.raduga.messagebus.messages.QueryAccountMessage;
import com.aqitrade.raduga.messagebus.messages.QueryCashMessage;
import com.aqitrade.raduga.messagebus.messages.QueryOrderStatusMessage;
import com.aqitrade.raduga.messagebus.messages.QueryPositionMessage;
import com.aqitrade.raduga.messagebus.messages.QuerySubscribeMarketDataMessage;
import com.aqitrade.raduga.messagebus.messages.SubscribeMarketDataMessage;
import com.aqitrade.raduga.messagebus.messages.UnsubscribeMarketDataMessage;

public class Decoders {

    public static Function<byte[], OrderMessage> orderMessage = (bytes) -> {
        return avroMessageDecode(bytes, OrderMessage.class);
    };
    
    public static Function<byte[], OrderStatusMessage> orderStatusMessage = (bytes) -> {
        return avroMessageDecode(bytes, OrderStatusMessage.class);
    };
    
    public static Function<byte[], CancelOrderMessage> cancelOrderMessage = (bytes) -> {
        return avroMessageDecode(bytes, CancelOrderMessage.class);
    };
    
    
    public static Function<byte[], QueryOrderStatusMessage> queryOrderStatusMessage = (bytes) -> {
        return avroMessageDecode(bytes, QueryOrderStatusMessage.class);
    };
    
    public static Function<byte[], QueryPositionMessage> queryPositionMessage = (bytes) -> {
        return avroMessageDecode(bytes, QueryPositionMessage.class);
    };
    
    public static Function<byte[], QueryCashMessage> queryCashMessage = (bytes) -> {
        return avroMessageDecode(bytes, QueryCashMessage.class);
    };
    
    public static Function<byte[], QueryAccountMessage> queryAccountMessage = (bytes) -> {
        return avroMessageDecode(bytes, QueryAccountMessage.class);
    };
    
    public static Function<byte[], QuerySubscribeMarketDataMessage> querySubscribeMarketDataMessage = (bytes) -> {
        return avroMessageDecode(bytes, QuerySubscribeMarketDataMessage.class);
    };
    
    public static Function<byte[], SubscribeMarketDataMessage> subscribeMarketDataMessage = (bytes) -> {
        return avroMessageDecode(bytes, SubscribeMarketDataMessage.class);
    };
 
    public static Function<byte[], UnsubscribeMarketDataMessage> unsubscribeMarketDataMessage = (bytes) -> {
        return avroMessageDecode(bytes, UnsubscribeMarketDataMessage.class);
    };
    

    private static <T extends GenericContainer> T avroMessageDecode(byte[] bytes, Class<T> clazz) {
        DatumReader<T> datumReader = new SpecificDatumReader<T>(clazz);
        try {
            return datumReader.read((T)null, DecoderFactory.get().binaryDecoder(bytes, null));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
