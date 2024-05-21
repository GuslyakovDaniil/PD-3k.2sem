package com.example.demo.DTO;

import java.util.List;

public class ReservationUpdateDto {

    private List<ReservationDto> reservations;

    public List<ReservationDto> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationDto> reservations) {
        this.reservations = reservations;
    }

    public static class ReservationDto {
        private Integer id;
        private Integer number;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }
    }
}
