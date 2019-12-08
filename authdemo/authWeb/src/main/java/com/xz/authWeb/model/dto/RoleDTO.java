package com.xz.authWeb.model.dto;

import java.io.Serializable;

public class RoleDTO implements Serializable {
	private static final long serialVersionUID = -239764408413663660L;

	private String name;
	private Long id;
	private String code;
	
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
