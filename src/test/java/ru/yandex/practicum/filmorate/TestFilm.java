package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestFilm {
    public FilmController filmController = new FilmController();
    Film templateFilm;
    String description = "a";

    @BeforeEach
    public void setData() {
        templateFilm = new Film("Зеленая миля", "Боль помечает наши лица", LocalDate.of(1999, 04, 18), 2);
    }

    @Test
    void createAnObject() {
        filmController.addFilm(templateFilm);
        assertEquals(1, filmController.getAllFilms().size(), "Валидация прошла");
    }

    @Test
    void createAnObjectWrongReleaseDate() {
        templateFilm.setReleaseDate(LocalDate.of(1880, 10, 10));
        assertThrows(ValidationException.class, () -> filmController.addFilm(templateFilm), "Неверная дата релиза");
    }

    @Test
    void createAnObjectEmptyName() {
        templateFilm.setName("");
        assertThrows(ValidationException.class, () -> filmController.addFilm(templateFilm), "Пустое имя");
    }

    @Test
    void createAnObjectMaximumDescription201() {
        templateFilm.setDescription(description.repeat(201));
        assertThrows(ValidationException.class, () -> filmController.addFilm(templateFilm), "Описание слишком длинное");
    }

    @Test
    void createAnObjectMaximumDescription200() {
        templateFilm.setDescription(description.repeat(200));
        filmController.addFilm(templateFilm);
        assertEquals(1, filmController.getAllFilms().size(), "Добавлен фильм с описанием в 200 символов");
    }

    @Test
    void createObjectDurationNull() {
        templateFilm.setDuration(0);
        assertThrows(ValidationException.class, () -> filmController.addFilm(templateFilm), "Неверная длительность");
    }

    @Test
    void createAnObjectPut() {
        filmController.addFilm(templateFilm);
        templateFilm.setId(1);
        templateFilm.setName("Зеленая миля 2");
        filmController.updateFilm(templateFilm);
        assertEquals(1, filmController.getAllFilms().size(), "Фильм обновлен");
    }
}