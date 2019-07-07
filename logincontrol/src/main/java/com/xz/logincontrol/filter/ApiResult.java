package com.xz.logincontrol.filter;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class ApiResult implements Serializable{
	private static final long serialVersionUID = 1L;

	private int code;
    private String msg;
    private Object data;
}
