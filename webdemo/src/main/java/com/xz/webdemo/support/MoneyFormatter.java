package com.xz.webdemo.support;

import java.text.ParseException;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.format.Formatter;

public class MoneyFormatter implements Formatter<Money>{

	@Override
	public String print(Money money, Locale locale) {
		if (null == money) {
			return null;
		}
		return money.getCurrencyUnit().getCode() + " " + money.getAmount();
	}

	/**
	 * 处理 CNY 10.00 or 10.00形式的字符串
	 */
	@Override
	public Money parse(String text, Locale locale) throws ParseException {
		if (NumberUtils.isParsable(text)) {
			return Money.of(CurrencyUnit.of("CNY"), NumberUtils.createBigDecimal(text));
		} else if (StringUtils.isNotEmpty(text)) {
			String[] split = StringUtils.split(text, " ");
			if (split != null && split.length == 2 && NumberUtils.isParsable(split[1])) {
				return Money.of(CurrencyUnit.of(split[0]), 
						NumberUtils.createBigDecimal(split[1]));
			}
		}
		throw new ParseException(text, 0);
	}
	
}
