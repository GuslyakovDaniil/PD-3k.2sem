package com.example.demo.Init;

import com.example.demo.model.bookModel;
import com.example.demo.model.reservationModel;
import com.example.demo.model.userModel;
import com.example.demo.repo.BookRepository;
import com.example.demo.repo.ReservationRepository;
import com.example.demo.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.util.Date;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class InitReservation implements CommandLineRunner {

    private final ReservationRepository reservationRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public InitReservation(ReservationRepository reservationRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        initReservations();
    }

    private void initReservations() {

        Time extradition = new Time(System.currentTimeMillis());

        if (reservationRepository.count() == 0) {
            createReservation(1, 1, new Date(), extradition, 2);

            createReservation(2, 1, new Date(), extradition, 1);
        }
    }

    private void createReservation(int bookId, int userId, Date reservationDate, Time extradition, int locationId) {
        bookModel book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));


        bookRepository.save(book);

        userModel user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        reservationModel reservation = new reservationModel();
        reservation.setBook(book);
        reservation.setUser(user);
        reservation.setReservation_date(reservationDate);
        reservation.setExtradition(extradition);
        reservation.setLocation_id(locationId);

        reservationRepository.save(reservation);
    }
}