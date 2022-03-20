package com.groupproject.nstu.cookbook.controller;

import com.groupproject.nstu.cookbook.entity.Dish;
import com.groupproject.nstu.cookbook.entity.Security;
import com.groupproject.nstu.cookbook.service.SecurityServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Security Check")
@RequestMapping("/login/")
public class SecurityController {

    private final SecurityServiceImpl securityService;

    public SecurityController(SecurityServiceImpl securityService) {
        this.securityService = securityService;
    }


    @GetMapping("/checkByNameAndPassword")
    public String findDishByNameAndPassword(String name, String password) {
        return securityService.getByNameAndPassword(name, password);
    }

}
