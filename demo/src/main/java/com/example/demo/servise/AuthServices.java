package com.example.demo.servise;

import com.example.demo.DTO.UserRegistration;
import com.example.demo.Enums.Role_enum;
import com.example.demo.model.roleModel;
import com.example.demo.model.userModel;
import com.example.demo.repo.RoleRopository;
import com.example.demo.repo.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServices {
    private final UserRepository userRepository;
    private final RoleRopository roleRopository;

    private final PasswordEncoder passwordEncoder;

    public AuthServices(UserRepository userRepository, RoleRopository roleRopository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRopository = roleRopository;
    }

    public void register(UserRegistration registrationDTO) {
        if (!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())) {
            throw new RuntimeException("passwords.match");
        }

        Optional<userModel> byEmail = this.userRepository.findByEmail(registrationDTO.getEmail());

        if (byEmail.isPresent()) {
            throw new RuntimeException("email.used");
        }

        var role = roleRopository.
                findRoleByName(Role_enum.USER).orElseThrow();

        userModel users = new userModel(
                registrationDTO.getUsername(),
                passwordEncoder.encode(registrationDTO.getPassword()),
                registrationDTO.getEmail(),
                registrationDTO.getTicket()
        );


        users.setRole((List<Role_enum>) role);
        this.userRepository.save(users);
    }

    public userModel getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " was not found!"));
    }
}
