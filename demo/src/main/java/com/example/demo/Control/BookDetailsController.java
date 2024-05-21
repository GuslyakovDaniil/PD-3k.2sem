package com.example.demo.Control;

import com.example.demo.DTO.AddReservationDto;
import com.example.demo.model.bookModel;
import com.example.demo.model.locationModel;
import com.example.demo.model.reservationModel;
import com.example.demo.model.userModel;
import com.example.demo.repo.BookRepository;
import com.example.demo.repo.LocationRepository;
import com.example.demo.repo.ReservationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.repo.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookDetailsController {

    private final BookRepository bookRepository;
    private final LocationRepository locationRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    public BookDetailsController(BookRepository bookRepository, LocationRepository locationRepository, ReservationRepository reservationRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.locationRepository = locationRepository;
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public String showBookDetails(@PathVariable("id") int id, Model model) {
        Optional<bookModel> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            bookModel book = optionalBook.get();
            model.addAttribute("book", book);

            List<locationModel> locations = locationRepository.findAll();
            model.addAttribute("locations", locations);

            AddReservationDto addReservationDto = new AddReservationDto();
            addReservationDto.setBook_id(book.getId());
            model.addAttribute("reservationDto", addReservationDto);

            return "book-details";
        } else {
            return "error";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditBookForm(@PathVariable("id") int id, Model model) {
        Optional<bookModel> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            bookModel book = optionalBook.get();
            model.addAttribute("book", book);
            return "edit-book";
        } else {
            return "error";
        }
    }

    @PostMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, @ModelAttribute("book") bookModel updatedBook) {
        Optional<bookModel> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            bookModel book = optionalBook.get();
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setGenre(updatedBook.getGenre());
            book.setDescription(updatedBook.getDescription());
            book.setNumber(updatedBook.getNumber());
            book.setLevel(updatedBook.isLevel());
            book.setLevel(true);
            bookRepository.save(book);
            return "redirect:/books/" + id;
        } else {
            return "error";
        }
    }

    @GetMapping("/{id}/delete")
    public String showDeleteBookForm(@PathVariable("id") int id, Model model) {
        Optional<bookModel> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            bookModel book = optionalBook.get();
            model.addAttribute("book", book);
            return "delete-book"; // Возвращаем страницу для подтверждения удаления
        } else {
            return "error";
        }
    }

    @PostMapping("/{id}/delete")
    @Transactional
    public String deleteBook(@PathVariable("id") int id) {
        bookRepository.deleteById(id);
        return "redirect:/";
    }


    @PostMapping("/reserve")
    public String reserveBook(@ModelAttribute("reservationDto") AddReservationDto reservationDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String currentPrincipalName = authentication.getName();

        Optional<userModel> optionalUser = userRepository.findByUsername(currentPrincipalName);

        if (optionalUser.isPresent()) {
            userModel currentUser = optionalUser.get();

            reservationDto.setUser_id(currentUser.getId());

            reservationModel reservation = new reservationModel();

            reservation.setReservation_date(reservationDto.getReservation_date());
            reservation.setExtradition(new Time(reservationDto.getExtradition().getTime()));
            reservation.setBook(bookRepository.findById(reservationDto.getBook_id()).orElse(null));
            reservation.setUser(currentUser);
            reservation.setLocation_id(reservationDto.getLocation_id());
            reservationRepository.save(reservation);
            bookModel book = bookRepository.findById(reservationDto.getBook_id()).orElse(null);
            if (book != null) {
                int newNumber = book.getNumber() - 1;
                book.setNumber(newNumber);
                book.setLevel(newNumber > 0);
                bookRepository.save(book);
            }

            return "redirect:/";
        } else {
            return "error";
        }
    }


}