package com.xz.logincontrol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	@GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("index")
    public String index() {
        return "index";
    }

    @GetMapping("/")
    public String _login() {
        return "login";
    }
}
