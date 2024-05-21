package com.example.demo.views;

public class UserWi {
    private String username;
    private String email;
    private Integer ticket;

    public UserWi(String username, String email, Integer ticket) {
        this.username = username;
        this.email = email;
        this.ticket = ticket;
    }

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

    public Integer getTicket() {
        return ticket;
    }

    public void setTicket(Integer ticket) {
        this.ticket = ticket;
    }
}
