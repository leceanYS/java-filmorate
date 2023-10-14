package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yandex.practicum.filmorate.validannotation.FilmReleaseDateConstraint;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Film {
    private int id;
    @NotBlank
    private String name;
    @Size(max=200)
    private String description;
    @FilmReleaseDateConstraint
    private LocalDate releaseDate;
    @Positive
    private long duration;
}