package ru.yandex.practicum.filmorate.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;

public class FilmControllerTest {
    private final FilmStorage storage = new InMemoryFilmStorage();
    private final UserStorage userStorage = new InMemoryUserStorage();
    private final UserService userService = new UserService(userStorage);
    private final FilmService service = new FilmService(storage, userService);
    private final FilmController controller = new FilmController(service);

    private final Film film = Film.builder()
            .id(1L)
            .name("Фильм на тест")
            .description("Описание")
            .releaseDate(LocalDate.of(2000, 12, 28))
            .duration(120)
            .build();

    @Test
    void addFilmTest() {
        controller.addFilm(film);

        Assertions.assertEquals(1, controller.getFilms().size());
    }

}