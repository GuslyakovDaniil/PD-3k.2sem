package com.example.demo.Unique;
import com.example.demo.repo.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
public class NameTicket implements ConstraintValidator<UniqueNameTicket, Integer> {
    private final UserRepository userRepository;

    public NameTicket(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return  userRepository.findByTicket(value).isEmpty();
}}
