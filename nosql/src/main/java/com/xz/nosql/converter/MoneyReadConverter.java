package com.xz.nosql.converter;

import org.bson.Document;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;

/**
 * 金额实体类型转换
 * @author xiaozhi009
 *
 */
public class MoneyReadConverter implements Converter<Document, Money> {

	@Override
	public Money convert(Document source) {
		Document money = (Document) source.get("money");
		double amount = Double.parseDouble(money.getString("amount"));
		String currency = ((Document)money.get("currency")).getString("code");
		return Money.of(CurrencyUnit.of(currency), amount);
	}
}
