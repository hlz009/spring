package com.xz.sessionredis.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xz.sessionredis.service.TestService;

@RestController
public class TestController {

	@Autowired
	private TestService testService;

	@RequestMapping("/hello")
	public String printSession(HttpSession session, String name) {
		String storedName = (String) session.getAttribute("name");
		if (storedName == null) {
			session.setAttribute("name", name);
			storedName = name;
		}
		return "hello " + storedName;
	}

	@RequestMapping("/user")
	public String getUser(String name) {
		return testService.getV(name);
	}
}
