package com.example.demo.repo;
import com.example.demo.model.bookModel;
import com.example.demo.model.userModel;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<bookModel,Integer> {
    Optional<userModel> findByNumber(Integer number);
    @Query("SELECT r.book FROM reservationModel r WHERE r.user.id = :userId")
    List<bookModel> findBorrowedBooksByUserId(@Param("userId") int userId);
    List<bookModel> findByTitleContainingOrAuthorContaining(String title, String author);
    List<bookModel> findByTitleContaining(String title);
}