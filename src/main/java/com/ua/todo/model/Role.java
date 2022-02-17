package com.ua.todo.model;


import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN("ADMIN"), USER("USER");

    public String getAuthority() {
        return name();
    }

    private String value;

    private Role(String value) {
        this.value = value;
    }

}
