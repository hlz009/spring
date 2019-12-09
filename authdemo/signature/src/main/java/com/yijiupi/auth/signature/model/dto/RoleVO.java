package com.yijiupi.auth.signature.model.dto;

import java.io.Serializable;

public class RoleVO implements Serializable {
	private static final long serialVersionUID = -239764408413663660L;

	private Long id;
	private String name;
	private String code;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
