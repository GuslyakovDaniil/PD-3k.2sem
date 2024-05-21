package com.example.demo.Unique;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = NameTicket.class)
public @interface UniqueNameTicket {
    String message() default "Такой номер уже существует!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
