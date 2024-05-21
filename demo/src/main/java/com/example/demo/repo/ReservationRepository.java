package com.example.demo.repo;

import com.example.demo.model.reservationModel;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<reservationModel,Integer> {


    @Query("SELECT r FROM reservationModel r WHERE r.reservation_date = :date")
    List<reservationModel> findByReservationDate(@Param("date") Date date);

    reservationModel findByNumber(Integer number);

    @Query("SELECT MIN(r.reservation_date) FROM reservationModel r WHERE r.user.id = :userId")
    Date findEarliestReservationDateByUserId(@Param("userId") int userId);

    List<reservationModel> findByUserId(int userId);
}
