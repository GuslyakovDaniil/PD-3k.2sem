package com.example.demo.DTO;

import com.example.demo.Enums.Role_enum;

public class InfoUserDto {
    private int id;
    private String email;
    private String username;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public InfoUserDto(){}
   }
