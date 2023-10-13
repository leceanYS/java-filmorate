package ru.yandex.practicum.validannotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ReleaseDateValidator.class)
public @interface FilmReleaseDateConstraint {
    String message() default "Must be after 28.12.1895";
    Class <?>[] groups() default {};
    Class < ? extends Payload > [] payload() default {};
}