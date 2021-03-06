package com.xz.hateoascustom.support;

import java.io.IOException;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

@JsonComponent
public class MoneyDeserializer extends StdDeserializer<Money> {
	private static final long serialVersionUID = 1L;

	protected MoneyDeserializer() {
		super(Money.class);
	}

	@Override
	public Money deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, 
	JsonProcessingException {
		return Money.of(CurrencyUnit.of("CNY"), p.getDecimalValue());
	}
}
