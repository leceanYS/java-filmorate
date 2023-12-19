package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    private Long id;
    @NotBlank
    @Email
    private String email;
    @NotBlank(message = "Поле login не может содержать пробелы")
    @Pattern(regexp = "\\S+", message = "Поле login не может содержать пробелы")
    private String login;
    private String name;
    @NotNull
    @PastOrPresent
    private LocalDate birthday;

}