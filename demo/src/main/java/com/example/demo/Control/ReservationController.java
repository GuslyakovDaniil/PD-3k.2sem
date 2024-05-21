package com.example.demo.Control;

import com.example.demo.DTO.ReservationUpdateDto;
import com.example.demo.model.userModel;
import com.example.demo.repo.ReservationRepository;
import com.example.demo.servise.NewUserServices;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import com.example.demo.DTO.AddReservationDto;
import com.example.demo.model.reservationModel;
import com.example.demo.servise.NewReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
    private final NewReservationService reservationService;
    private final NewUserServices userService;

    private final ReservationRepository reservationRepository;
    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);


    public ReservationController(NewReservationService reservationService, NewUserServices userService,ReservationRepository reservationRepository) {
        this.reservationService = reservationService;
        this.userService = userService;
        this.reservationRepository = reservationRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addReservation(@ModelAttribute AddReservationDto reservationDto) { // Removed unnecessary parameters
        reservationService.addReservation(reservationDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping("/all")
    public ResponseEntity<List<AddReservationDto>> allReservations() {
        List<AddReservationDto> reservations = reservationService.allReservation();
        return ResponseEntity.ok(reservations);
    }



    @GetMapping("/details/{id}")
    public String showUserDetails(@PathVariable int id, @RequestParam("selectedDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date selectedDate, Model model) {
        userModel user = userService.getUserById(id);
        List<reservationModel> reservations = user.getReservations();
        List<reservationModel> reservationsOnSelectedDate = new ArrayList<>();
        Date selectedDateWithoutTime = new Date(selectedDate.getTime());
        for (reservationModel reservation : reservations) {
            Date reservationDateWithoutTime = new Date(reservation.getReservation_date().getTime());
            if (selectedDateWithoutTime.equals(reservationDateWithoutTime)) {
                reservationsOnSelectedDate.add(reservation);
            }
        }
        model.addAttribute("reservations", reservationsOnSelectedDate);
        model.addAttribute("user", user);
        return "user-on-calendar";
    }

    private boolean isSameDay(Date date1, Date date2) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(date1).equals(fmt.format(date2));
    }
    @PostMapping("/remove/{id}")
    public ResponseEntity<Void> removeReservation(@PathVariable int id) {
        try {
            reservationService.removeReservation(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // Логирование ошибки
            logger.error("Ошибка при удалении бронирования с id " + id, e);
            // Возвращаем ошибку сервера
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/view")
    public String showCalendarView(Model model) {
        List<AddReservationDto> reservations = reservationService.allReservation();
        model.addAttribute("reservations", reservations);
        return "reservation-calendar";
    }

    @GetMapping("/users")
    public String showUsersByDate(@RequestParam("date")  String date, Model model) {
        List<reservationModel> reservationsByDate = reservationService.getReservationsByDate(date);
        Set<userModel> uniqueUsers = new HashSet<>();

        for (reservationModel reservation : reservationsByDate) {
            userModel user = reservation.getUser();
            if (user != null) {
                uniqueUsers.add(user);
            }
        }
        List<userModel> users = new ArrayList<>(uniqueUsers);

        model.addAttribute("users", users);
        model.addAttribute("selectedDate", date);

        return "user-list-by-date";
    }

    @GetMapping("/detailsById/{id}")
    public ResponseEntity<AddReservationDto> getReservationDetailsById(@PathVariable int id) {
        AddReservationDto reservationDetails = reservationService.reservationDetails(id);
        String formattedExtraditionDate = reservationService.formatDate(reservationDetails.getExtradition());
        String formattedReservationDate = reservationService.formatDate(reservationDetails.getReservation_date());
        return ResponseEntity.ok(reservationDetails);
    }

    @PostMapping("/updateReservation")
    public String updateReservation(@ModelAttribute ReservationUpdateDto reservationUpdateDto) {
        List<ReservationUpdateDto.ReservationDto> reservations = reservationUpdateDto.getReservations();
        for (ReservationUpdateDto.ReservationDto reservationDto : reservations) {
            reservationModel reservation = reservationRepository.findById(reservationDto.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid reservation Id:" + reservationDto.getId()));
            reservation.setNumber(reservationDto.getNumber());
            reservationRepository.save(reservation);
        }
        return "redirect:/reservations/view";
    }
}