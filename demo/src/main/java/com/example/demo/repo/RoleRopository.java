package com.example.demo.repo;

import com.example.demo.Enums.Role_enum;
import com.example.demo.model.roleModel;
import com.example.demo.model.userModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRopository extends JpaRepository<roleModel,Integer> {
    Optional<roleModel> findRoleByName(Role_enum role_enum);
}
