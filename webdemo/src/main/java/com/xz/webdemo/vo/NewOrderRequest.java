package com.xz.webdemo.vo;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class NewOrderRequest {
	@NotEmpty
    private String customer;
    @NotEmpty
    private List<String> items;
}
