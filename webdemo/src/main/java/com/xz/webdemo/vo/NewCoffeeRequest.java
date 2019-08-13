package com.xz.webdemo.vo;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.joda.money.Money;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NewCoffeeRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	@NotEmpty
	private String name;
	@NotNull
	private Money price;
}
