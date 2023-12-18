package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class Genre {

    @Positive
    protected Long id;

    @NotBlank
    protected String name;

}
