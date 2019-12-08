package com.xz.authWeb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xz.authWeb.annotation.SignAnnotation;
import com.xz.authWeb.model.dto.RoleDTO;

@RestController
@SignAnnotation
public class TestController {

    
    @GetMapping(value = "roles/checkRoleCode")
    public void checkRoleCode(String signature, @RequestParam String code, @RequestParam Long id) {
//    	return roleService.checkRoleCodeNoUnique(code, id);
    	System.out.println("调用成功！roles/checkRoleCode");
    }

    @PostMapping(value = "roles/addRole")
    public void addRole(@RequestBody RoleDTO roleDTO) {
//    	validateRoleDTO(roleDTO);
//    	return roleService.addRole(roleDTO);
    	System.out.println("调用成功！roles/addRole");
    }
}
