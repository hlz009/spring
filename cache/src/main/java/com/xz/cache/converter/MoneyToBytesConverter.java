package com.xz.cache.converter;

import java.nio.charset.StandardCharsets;

import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class MoneyToBytesConverter implements Converter<Money, byte[]>{
	@Override
	public byte[] convert(Money source) {
		String value = Long.toString(source.getAmountMinorLong());
		return value.getBytes(StandardCharsets.UTF_8);
	}
}
