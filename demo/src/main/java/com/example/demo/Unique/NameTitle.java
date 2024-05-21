package com.example.demo.Unique;
import com.example.demo.repo.BookRepository;
import com.example.demo.repo.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
public class NameTitle implements ConstraintValidator<UniqueNameTicket, Integer> {
    private final BookRepository bookRepository;

    public NameTitle(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return  bookRepository.findByNumber(value).isEmpty();
    }}
