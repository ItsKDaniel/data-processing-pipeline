package io.falcon.assignment.validators;

import io.falcon.assignment.exceptions.BadRequestException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateValidator implements ConstraintValidator<Date, String> {

    DateTimeFormatter formatter;

    @Override
    public void initialize(Date constraintAnnotation) {
        this.formatter = DateTimeFormatter.ofPattern(constraintAnnotation.pattern());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            LocalDateTime.parse(value, formatter);
            return true;
        } catch (Exception ex) {
            throw BadRequestException.invalidDate();
        }
    }
}
