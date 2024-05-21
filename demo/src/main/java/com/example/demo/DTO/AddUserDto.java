package com.example.demo.DTO;

import com.example.demo.Enums.Role_enum;
import com.example.demo.Unique.UniqueNameTicket;
import com.example.demo.model.bookModel;

import java.util.List;

public class AddUserDto {

    private int id;
    private String username;
    private String password;
    @UniqueNameTicket
    private int ticket;
    private Role_enum role_enum;
    private String email;
    private List<bookModel> borrowedBooks;

    private List<AddReservationDto> reservations; // Новое поле для бронирований

    public List<AddReservationDto> getReservations() {
        return reservations;
    }

    public void setReservations(List<AddReservationDto> reservations) {
        this.reservations = reservations;
    }

    public List<bookModel> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<bookModel> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTicket() {
        return ticket;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }

    public Role_enum getRole_enum() {
        return role_enum;
    }

    public void setRole_enum(Role_enum role_enum) {
        this.role_enum = role_enum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AddUserDto(){
    }
    public AddUserDto(String username,String password, int ticket,Role_enum role_enum,String email){
        this.email = email;
        this.ticket = ticket;
        this.username = username;
        this.password = password;
        this.role_enum = role_enum;
    }
}
