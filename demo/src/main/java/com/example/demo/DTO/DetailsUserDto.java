package com.example.demo.DTO;

public class DetailsUserDto {
    private int id;
    private int ticket;
    private String email;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTicket() {
        return ticket;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public DetailsUserDto(){}
    public DetailsUserDto(String email, String username,int ticket){
        this.email = email;
        this.username = username;
        this.ticket = ticket;
    }
}


