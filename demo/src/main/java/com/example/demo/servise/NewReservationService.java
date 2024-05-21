package com.example.demo.servise;

import com.example.demo.DTO.AddReservationDto;
import com.example.demo.model.bookModel;
import com.example.demo.model.reservationModel;
import com.example.demo.model.userModel;
import com.example.demo.repo.BookRepository;
import com.example.demo.repo.ReservationRepository;
import com.example.demo.repo.UserRepository;
import org.modelmapper.ModelMapper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class NewReservationService {
    private final  ReservationRepository reservationRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public NewReservationService(ReservationRepository reservationRepository, BookRepository bookRepository, UserRepository userRepository, ModelMapper mapper) {
        this.reservationRepository = reservationRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }
    public void addReservation(AddReservationDto reservationDto) {
        reservationModel reservation = mapper.map(reservationDto, reservationModel.class);
        bookModel book = bookRepository.findById(reservationDto.getBook_id())
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
        userModel user = userRepository.findById(reservationDto.getUser_id())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        reservation.setBook(book);
        reservation.setUser(user);
        reservationRepository.saveAndFlush(reservation);
    }

    public List<AddReservationDto> allReservation() {
        return reservationRepository.findAll().stream().map(reservation -> mapper.map(reservation, AddReservationDto.class))
                .collect(Collectors.toList());
    }

    public AddReservationDto reservationDetails(int id) {
        return mapper.map(reservationRepository.findById(id).orElse(null), AddReservationDto.class);
    }

    public void removeReservation(int id) {
        reservationRepository.deleteById(id);
    }

    public List<reservationModel> getReservationsByDate(String dateString) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            return reservationRepository.findByReservationDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Date> getReservedDates(Date startDate, Date endDate) {
        List<Date> reservedDates = new ArrayList<>();
        List<reservationModel> reservations = reservationRepository.findAll();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String startDateStr = df.format(startDate);
        String endDateStr = df.format(endDate);

        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        while (!startCal.after(endCal)) {
            String currentDateString = df.format(startCal.getTime());
            for (reservationModel reservation : reservations) {
                String reservationDateString = df.format(reservation.getReservation_date());
                if (currentDateString.equals(reservationDateString)) {
                    reservedDates.add(startCal.getTime());
                    break;
                }
            }
            startCal.add(Calendar.DATE, 1);
        }

        return reservedDates;
    }

    public Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.parse(dateString);
    }

    // vmklsd
    public String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(date);
    }
    public List<reservationModel> findReservationsByUserId(int userId) {
        return reservationRepository.findByUserId(userId);
    }
}


