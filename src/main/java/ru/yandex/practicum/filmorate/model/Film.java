package ru.yandex.practicum.filmorate.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Value;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.validannotation.interfaces.DateTimeValidatorCustom;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Set;

@Value
@Builder(toBuilder = true)
public class Film {
    private static final LocalDate MIN_DATE = LocalDate.of(1895, 12, 28);
    int id;
    @NotBlank(message = "Title can not be empty") @NotNull
    String name;
    @Length(max = 200, message = "Description should be less than 200 length")
    String description;
    @NotNull(message = "Release date is incorrect")
    @DateTimeValidatorCustom(max = "28/12/1895", message = "Release date less than min release date")
    LocalDate releaseDate;
    @Positive(message = "Duration should be greater than 0")
    int duration;
    @Builder.Default
    int rate = 0;
    @JsonBackReference
    Set<Integer> likes;
}