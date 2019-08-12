package com.xz.webdemo.support;

import java.io.IOException;

import org.joda.money.Money;
import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * moneyjson序列化
 * @author xiaozhi009
 * @time 2019年7月15日下午10:11:24
 */
@JsonComponent
public class MoneySerializer extends StdSerializer<Money> {

	private static final long serialVersionUID = 1L;

	protected MoneySerializer() {
        super(Money.class);
	}

    @Override
    public void serialize(Money money, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(money.getAmount());
    }
}
