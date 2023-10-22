package ru.yandex.practicum.filmorate.validannotation;

import ru.yandex.practicum.filmorate.validannotation.interfaces.DateTimeValidatorCustom;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeValidator implements ConstraintValidator<DateTimeValidatorCustom, LocalDate> {

    private LocalDate dateMin;

    @Override
    public void initialize(DateTimeValidatorCustom constraintAnnotation) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(constraintAnnotation.pattern());
        dateMin = LocalDate.from(dateTimeFormatter.parse(constraintAnnotation.max()));
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        return localDate  == null || localDate.isAfter(dateMin);
    }
}