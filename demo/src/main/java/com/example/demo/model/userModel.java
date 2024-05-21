package com.example.demo.model;

import com.example.demo.Enums.Role_enum;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name="users")
public class userModel extends BaseEntity {
    private String username;
    private String password;
    private String email;
    private int ticket;
    private List<Role_enum> role;



    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<reservationModel> reservations = new ArrayList<>();
    public List<reservationModel> getReservedBooks() {
        return reservations;
    }

    public void setReservedBooks(List<reservationModel> reservedBooks) {
        this.reservations = reservedBooks;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private locationModel location;

    public locationModel getLocation() {
        return location;
    }

    public void setLocation(locationModel location) {
        this.location = location;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    public List<Role_enum> getRole() {
        return role;
    }

    public void setRole(List<Role_enum> role) {
        this.role = role;
    }

    public userModel(String username, String encode, String email, int ticket) {
        this.username = username;
        this.password = encode;
        this.email = email;
        this.ticket = ticket;
    }

    public List<reservationModel> getReservations() {
        return reservations;
    }

    public void setReservations(List<reservationModel> reservations) {
        this.reservations = reservations;
    }

    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "ticket")
    public int getTicket() {
        return ticket;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }

    public userModel() {}
}
