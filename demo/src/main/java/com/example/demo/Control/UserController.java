package com.example.demo.Control;


import com.example.demo.DTO.AddBookDto;
import com.example.demo.DTO.AddUserDto;
import com.example.demo.DTO.DetailsUserDto;
import com.example.demo.DTO.InfoUserDto;
import com.example.demo.model.bookModel;
import com.example.demo.model.reservationModel;
import com.example.demo.model.userModel;
import com.example.demo.servise.NewBookService;
import com.example.demo.servise.NewReservationService;
import com.example.demo.servise.NewUserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import  org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.management.relation.RoleNotFoundException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private final NewUserServices userService;
    private final NewReservationService reservationService;

    public UserController(NewUserServices userService,NewReservationService reservationService) {
        this.userService = userService;
        this.reservationService = reservationService;
    }

    @ModelAttribute("userModel")
    public AddUserDto initUser(){
        return new AddUserDto();
    }

    @GetMapping("/books")
    public String showUserBooks(Model model, Principal principal) {
        String username = principal.getName();
        List<bookModel> userBooks = userService.getUserBooks(username);
        model.addAttribute("books", userBooks);
        return "profile";
    }

    @PostMapping("/add")
    public String addUser(@Valid AddUserDto userModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userModel", userModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userModel", bindingResult);
            return "redirect:/users/add";
        }

        userService.addUsers(userModel);

        System.out.println("Email: " + userModel.getEmail());
        System.out.println("Password: " + userModel.getPassword());

        return "redirect:/";
    }


    @GetMapping("/add")
    public String addUser(Model model, Principal principal) {
        boolean isAdmin = false;
        if (principal != null) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            isAdmin = userDetails.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_MODERATOR"));
        }
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("userModel", new AddUserDto());

        return "user-add";
    }



    @GetMapping("/all")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.allUsers());
        return "user-all";
    }

    @GetMapping("/details/{id}")
    public ModelAndView getUserDetails(@PathVariable int id) throws ChangeSetPersister.NotFoundException {
        AddUserDto userDetails = userService.getUserDetailsById(id);
        List<bookModel> borrowedBooks = NewBookService.getBorrowedBooksByUserId(id);
        userDetails.setBorrowedBooks(borrowedBooks);

        List<reservationModel> reservations = reservationService.findReservationsByUserId(id);

        ModelAndView modelAndView = new ModelAndView("user-details");
        modelAndView.addObject("userDetails", userDetails);
        modelAndView.addObject("reservations", reservations); // Передача списка бронирований в представление

        return modelAndView;
    }


    @DeleteMapping("/remove/{email}")
    public ResponseEntity<Void> removeUser(@PathVariable String email) {
        userService.removeUser(email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/details/update/{id}")
    public String showUpdateForm(@PathVariable("id") int userId, Model model) throws ChangeSetPersister.NotFoundException {

        AddUserDto userDetails = userService.getUserDetailsById(userId);
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("user_id", userId);
        return "user-update";
    }

    @PostMapping("/details/update/{id}")
    public String updateUser(@PathVariable("id") int userId, AddUserDto updatedUserDetails) {
        updatedUserDetails.setId(userId);
        userService.updateUser(updatedUserDetails);
        return "redirect:/users/details/{id}";

    }

    @PostMapping("/remove/{id}")
    public String removeUserById(@PathVariable int id) {
        userService.removeUserById(id);
        return "redirect:/users/all";
    }

    @GetMapping("/search")
    public String searchUsers(@RequestParam("search") String searchQuery, Model model) {
        List<userModel> foundUsers = userService.searchUsers(searchQuery);
        model.addAttribute("users", foundUsers);
        return "user-all";
    }

}

