package com.example.demo.repo;
import com.example.demo.model.bookModel;
import com.example.demo.model.userModel;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<userModel,Integer> {

    Optional<userModel> findByTicket(int ticket);

    Optional<userModel> findByUsername(String username);
    @Modifying
    @Transactional
    void deleteByEmail(String email);

    Optional<userModel> findByEmail(String email);

    List<userModel> findUsersByTicket(int ticket);

    List<userModel> findUsersByUsernameContainingOrEmailContaining(String username, String email);
}

