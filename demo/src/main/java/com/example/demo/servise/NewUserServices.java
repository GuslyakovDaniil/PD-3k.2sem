package com.example.demo.servise;

import com.example.demo.DTO.AddUserDto;
import com.example.demo.DTO.DetailsUserDto;
import com.example.demo.DTO.InfoUserDto;
import com.example.demo.DTO.UserRegistration;
import com.example.demo.Enums.Role_enum;
import com.example.demo.model.bookModel;
import com.example.demo.model.roleModel;
import com.example.demo.model.userModel;
import com.example.demo.repo.BookRepository;
import com.example.demo.repo.RoleRopository;
import com.example.demo.repo.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NewUserServices {
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public NewUserServices(UserRepository usersRepository, ModelMapper mapper, PasswordEncoder passwordEncoder) {
        this.userRepository = usersRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<bookModel> getUserBooks(String username) {
        Optional<userModel> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return user.get().getReservations().stream()
                    .map(reservation -> reservation.getBook())
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public void addUsers(AddUserDto addUsersDto) {
        userModel user = mapper.map(addUsersDto, userModel.class);
        user.setPassword(passwordEncoder.encode(addUsersDto.getPassword()));

        List<Role_enum> roles = new ArrayList<>();
        roles.add(addUsersDto.getRole_enum());
        user.setRole(roles);

        userRepository.saveAndFlush(user);
    }

    public List<AddUserDto> allUsers() {
        return userRepository.findAll().stream().map(users -> mapper.map(users, AddUserDto.class))
                .collect(Collectors.toList());
    }

    public AddUserDto getUserDetailsById(int id) throws ChangeSetPersister.NotFoundException {
        userModel user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new ChangeSetPersister.NotFoundException();
        }
        return mapper.map(user, AddUserDto.class);
    }
    public void updateUser(AddUserDto updatedUserDetails) {
        userRepository.findById(updatedUserDetails.getId()).ifPresent(user -> {
            user.setUsername(updatedUserDetails.getUsername());
            user.setPassword(updatedUserDetails.getPassword());
            user.setEmail(updatedUserDetails.getEmail());
            user.setTicket(updatedUserDetails.getTicket());
            user.setRole(Collections.singletonList(updatedUserDetails.getRole_enum()));

            userRepository.save(user);
        });
    }

    public userModel getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<userModel> searchUsers(String search) {
        try {
            int ticket = Integer.parseInt(search);
            return userRepository.findUsersByTicket(ticket);
        } catch (NumberFormatException e) {
            return userRepository.findUsersByUsernameContainingOrEmailContaining(search, search);
        }
    }

    public void removeUser(String email) {
        userRepository.deleteByEmail(email);
    }

    public void removeUserById(int id) {
        userRepository.deleteById(id);
    }



}
