package com.example.demo.Init;

import com.example.demo.DTO.AddUserDto;
import com.example.demo.Enums.Role_enum;
import com.example.demo.model.roleModel;
import com.example.demo.model.userModel;
import com.example.demo.repo.RoleRopository;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class InitUser implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRopository roleRopository;
    private final PasswordEncoder passwordEncoder;
    private final String defaultPassword;

    public InitUser(UserRepository userRepository, RoleRopository roleRopository, PasswordEncoder passwordEncoder, @Value("${app.default.password}") String defaultPassword) {
        this.userRepository = userRepository;
        this.roleRopository = roleRopository;
        this.passwordEncoder = passwordEncoder;
        this.defaultPassword = defaultPassword;
    }

    @Override
    public void run(String... args) throws Exception {
        initRoles();
        initUsers();
    }

    private void initRoles() {
        if (roleRopository.count() == 0) {
            var moderatorRole = new roleModel(Role_enum.MODERATOR);
            var normalUserRole = new roleModel(Role_enum.USER);
            var libraryRole = new roleModel(Role_enum.LIBRARY);
            roleRopository.saveAndFlush(libraryRole);
            roleRopository.save(moderatorRole);
            roleRopository.save(normalUserRole);
        }
    }

    private void initUsers() {
        if (userRepository.count() == 0) {
            initModerator();
            initNormalUser();
            initLibrary();
        }
    }

    private void initLibrary(){
        var libraryRole = roleRopository.
                findRoleByName(Role_enum.LIBRARY).orElseThrow();
        var libraryUser = new userModel("library",passwordEncoder.encode(defaultPassword),"iotrp",7877);
        libraryUser.setRole(Collections.singletonList((libraryRole.getName())));

        userRepository.save(libraryUser);
    }
    private void initModerator(){

        var moderatorRole = roleRopository.
                findRoleByName(Role_enum.MODERATOR).orElseThrow();

        var moderatorUser = new userModel("moderator", passwordEncoder.encode(defaultPassword), "ppppp", 4545);
        moderatorUser.setRole(Collections.singletonList(moderatorRole.getName()));

        userRepository.save(moderatorUser);
    }

    private void initNormalUser(){
        var userRole = roleRopository.
                findRoleByName(Role_enum.USER).orElseThrow();

        var normalUser = new userModel("user", passwordEncoder.encode(defaultPassword),  "qqqqq",78787);
        normalUser.setRole(Collections.singletonList(userRole.getName()));

        userRepository.save(normalUser);
    }
}