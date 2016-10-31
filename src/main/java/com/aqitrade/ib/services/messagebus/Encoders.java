package com.aqitrade.ib.services.messagebus;

import com.aqitrade.raduga.messagebus.messages.*;
import org.apache.avro.generic.GenericContainer;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.function.Function;

public class Encoders {

    public static Function<OrderMessage, byte[]> orderMessage = (message) -> {
        return avroMessageEncode(message, OrderMessage.class);
    };

    private static <T extends GenericContainer> byte[] avroMessageEncode(T message, Class<T> clazz) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        DatumWriter<T> datumWriter = new SpecificDatumWriter<T>(clazz);
        try {
            datumWriter.write(message, EncoderFactory.get().directBinaryEncoder(os, null));
            return os.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
