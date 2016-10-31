package com.aqitrade.ib.services.messagebus.impl.rabbitmq;

import java.io.IOException;
import java.util.function.Function;

import org.apache.avro.generic.GenericContainer;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;

import com.aqitrade.raduga.messagebus.messages.OrderMessage;

public class Decoders {

    public static Function<byte[], OrderMessage> orderMessage = (bytes) -> {
        return avroMessageDecode(bytes, OrderMessage.class);
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
