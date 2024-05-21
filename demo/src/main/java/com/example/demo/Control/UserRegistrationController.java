package com.example.demo.Control;

import com.example.demo.DTO.UserRegistration;
import com.example.demo.model.bookModel;
import com.example.demo.model.userModel;
import com.example.demo.repo.ReservationRepository;
import com.example.demo.servise.AuthServices;
import com.example.demo.servise.NewUserServices;
import com.example.demo.views.UserWi;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/users")
public class UserRegistrationController {

    private final AuthServices authServices;
    private final ReservationRepository reservationRepository;

    private final NewUserServices userService;

    public UserRegistrationController(AuthServices authServices, ReservationRepository reservationRepository, NewUserServices userService) {
        this.authServices = authServices;
        this.reservationRepository = reservationRepository;
        this.userService = userService;
    }
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userRegistration", new UserRegistration());
        return "register";
    }

    @PostMapping("/register")
    public String registerUserAccount(@ModelAttribute("userRegistration") @Valid UserRegistration registrationDto,
                                      BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }

        authServices.register(registrationDto);
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login-error")
    public String loginError() {
        return "redirect:/users/login?error";
    }

    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {
        String username = principal.getName();
        userModel user = authServices.getUser(username);
        Date earliestReservationDate = reservationRepository.findEarliestReservationDateByUserId(user.getId());

        List<bookModel> userBooks = userService.getUserBooks(username);
        UserWi userWi = new UserWi(
                user.getUsername(),
                user.getEmail(),
                user.getTicket()
        );
        model.addAttribute("user", userWi);
        model.addAttribute("earliestReservationDate", earliestReservationDate);
        model.addAttribute("books", userBooks);

        return "profile";
    }



}

