package com.xz.webdemo.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.joda.money.Money;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NewCoffeeRequest {
	@NotEmpty
	private String name;
	@NotNull
	private Money price;
}
