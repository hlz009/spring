package com.xz.logincontrol.pojo;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserBO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String username;
    private String password;
    private String realName;
    private boolean kickout;
}
