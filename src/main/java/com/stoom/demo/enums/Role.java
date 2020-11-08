package com.stoom.demo.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN, ROLE_EMPLOYEE, ROLE_USER, ROLE_PUBLISHER, ROLE_CUSTOMER;

    public String getAuthority() {
        return name();
    }

    public static Role getRole(String s){
        switch (s) {
            case "ROLE_ADMIN": return ROLE_ADMIN;
            case "ROLE_EMPLOYEE": return ROLE_EMPLOYEE;
            case "ROLE_USER": return ROLE_USER;
            case "ROLE_CUSTOMER": return ROLE_CUSTOMER;
            case "ROLE_PUBLISHER": return ROLE_PUBLISHER;
            default: return null;
        }
    }
}

