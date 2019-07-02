package com.xz.logincontrol.pojo;

import java.io.Serializable;


public class CurrentUser implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final ThreadLocal<UserBO> currentUser = new ThreadLocal<>();
	
	public static void put(UserBO userBO) {
        currentUser.set(userBO);
    }
    public static UserBO get() {
        return currentUser.get();
    }
}
