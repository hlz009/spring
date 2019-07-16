package com.xz.logincontrol.pojo;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class UserBO implements Serializable {
	private static final long serialVersionUID = 1L;
	@NotEmpty
	private String username;
	@NotEmpty
    private String password;
    private String realName;
    private boolean kickout;
}
