package com.groupproject.nstu.cookbook.service;

import com.groupproject.nstu.cookbook.entity.Security;
import com.groupproject.nstu.cookbook.service.interfaces.SecurityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Override
    public String getByNameAndPassword(String name, String password) {

        if(name.equals("admin") && password.equals("admin")){
            Security security = new Security();
            security.setName(name);
            security.setPassword(password);
            security.setRole("ROLE_ADMIN");
            return "ROLE_ADMIN";
        }

        return "ROLE_USER";

    }
}
