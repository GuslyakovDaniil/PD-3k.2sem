package com.example.demo.config;

import com.example.demo.repo.BookRepository;
import com.example.demo.repo.ReservationRepository;
import com.example.demo.repo.UserRepository;
import com.example.demo.servise.NewReservationService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public NewReservationService newReservationService(ReservationRepository reservationRepository, BookRepository bookRepository, UserRepository userRepository, ModelMapper mapper) {
        return new NewReservationService(reservationRepository, bookRepository, userRepository, mapper);
    }
}
