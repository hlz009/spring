package com.xz.logincontrol.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.xz.logincontrol.constant.enums.ArgumentResponseEnum;
import com.xz.logincontrol.constant.enums.CommonResponseEnum;
import com.xz.logincontrol.filter.ApiResult;
import com.xz.logincontrol.pojo.CurrentUser;
import com.xz.logincontrol.pojo.UserBO;
import com.xz.logincontrol.service.UserService;

@RestController
public class LoginApiController {
	@Autowired
    private UserService userService;

    @PostMapping("login")
    public ApiResult login(@Valid @RequestBody UserBO userBO, BindingResult bindResult) {
    	ArgumentResponseEnum.VALID_ERROR.assertValid(bindResult);
    	return new ApiResult(200, "登录成功", userService.buildUserInfo(userBO));
    }

    @GetMapping("user/info")
    public UserBO info() {
    	CommonResponseEnum.SERVER_BUSY.assertFail();
        return CurrentUser.get();
    }

    @PostMapping("logout")
    public void logout(@RequestHeader("Authorization") String jwt) {
        userService.logout(jwt);
//        return new ApiResult(200, "成功", null);
    }

}
