package com.example.demo.model;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.util.*;

@Entity
@Table(name="reservations")
public class reservationModel extends BaseEntity{
    @Column(name = "reservation_date")
    @Temporal(TemporalType.DATE)
    private Date reservation_date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private userModel user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private bookModel book;


    @Column(name = "extradition")
    @Temporal(TemporalType.TIME)
    private Time extradition;

    @Column(name = "location_id")
    private Integer location_id;

    @Column(name = "number")
    private Integer number;


    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public bookModel getBook() {
        return book;
    }

    public void setBook(bookModel book) {
        this.book = book;
    }

    public userModel getUser() {
        return user;
    }

    public void setUser(userModel user) {
        this.user = user;
    }

    public Date getReservation_date() {
        return reservation_date;
    }

    public void setReservation_date(Date reservation_date) {
        this.reservation_date = reservation_date;
    }

    public Date getReturn_date() {
        return extradition;
    }

    public reservationModel(){}

    public Integer getLocation_id() {
        return location_id;
    }

    public void setLocation_id(Integer location_id) {
        this.location_id = location_id;
    }

    public Time getExtradition() {
        return extradition;
    }

    public void setExtradition(Time extradition) {
        this.extradition = extradition;
    }
}
