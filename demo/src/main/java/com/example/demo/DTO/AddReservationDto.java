package com.example.demo.DTO;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class AddReservationDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date reservation_date;
    @DateTimeFormat(pattern = "HH:mm")
    private Date extradition;
    private int book_id;
    private int user_id;
    private int location_id;
    private int number;

    public AddReservationDto() {}

    public AddReservationDto(Date reservation_date, Date extradition, int book_id, int user_id, int location_id, int number) {
        this.reservation_date = reservation_date;
        this.extradition = extradition;
        this.book_id = book_id;
        this.user_id = user_id;
        this.location_id = location_id;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getReservation_date() {
        return reservation_date;
    }

    public void setReservation_date(Date reservation_date) {
        this.reservation_date = reservation_date;
    }

    public Date getExtradition() {
        return extradition;
    }

    public void setExtradition(Date extradition) {
        this.extradition = extradition;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }
}
