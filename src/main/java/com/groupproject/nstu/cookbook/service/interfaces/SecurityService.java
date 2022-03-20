package com.groupproject.nstu.cookbook.service.interfaces;

import com.groupproject.nstu.cookbook.entity.Security;

public interface SecurityService {

    String getByNameAndPassword(String name, String password);
}
