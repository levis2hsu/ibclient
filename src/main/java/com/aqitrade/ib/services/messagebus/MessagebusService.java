package com.aqitrade.ib.services.messagebus;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class MessagebusService {

    public <T> void subscribe(Class<T> messageType, Consumer<T> callback, Function<byte[], T> decoder) {
      subscribe(messageType, callback, decoder, UUID.randomUUID().toString());
    }

    public abstract <T> void subscribe(Class<T> messageType, Consumer<T> callback, Function<byte[], T> decoder, String consumerGroup);

    public abstract <T> T produce(T message, Function<T, byte[]> encoder);

}
