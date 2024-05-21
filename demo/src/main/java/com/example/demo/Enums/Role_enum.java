package com.example.demo.Enums;

public enum Role_enum {
    USER(0),MODERATOR(1),LIBRARY(2);

    public String getName() {
        return name();
    }

    private final int roles;
    Role_enum(int roles){
        this.roles = roles;
    }
    public  int getRoles(){return roles;}
}
