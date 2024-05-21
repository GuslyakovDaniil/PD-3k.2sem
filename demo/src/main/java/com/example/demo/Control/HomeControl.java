package com.example.demo.Control;

import com.example.demo.DTO.UserRegistration;
import com.example.demo.servise.AuthServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

public class HomeControl {
    @Controller
    @RequestMapping("/users")
    public class UserRegistrationController {

        private AuthServices authServices;

        @Autowired
        public UserRegistrationController(AuthServices authServices) {
            this.authServices = authServices;
        }

        @ModelAttribute("userRegistrationDto")
        public UserRegistration initForm() {
            return new UserRegistration();
        }

        @GetMapping("/register")
        public String register() {
            return "register";
        }

//        @PostMapping("/register"){}
}}
