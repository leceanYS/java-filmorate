package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

    @Autowired
    public final FilmService filmService;

    @GetMapping
    public List<Film> getFilms() {
        log.info("Пришел GET запрос /films");
        List<Film> response = filmService.getFilms();
        log.info("Отправлен ответ GET /films с телом: {}", response);
        return response;
    }

    @PostMapping
    public Film addFilm(@RequestBody @Valid Film film) {
        log.info("Пришел POST запрос /films с телом {}", film);
        Film response = filmService.addFilm(film);
        log.info("Отправлен ответ POST /films с телом: {}", response);
        return response;
    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid Film film) {
        log.info("Пришел PUT запрос /films с телом {}", film);
        Film response = filmService.updateFilm(film);
        log.info("Отправлен ответ PUT /films с телом: {}", response);
        return response;
    }

    @DeleteMapping("/{id}")
    public void deleteFilm(@PathVariable Long id) {
        log.info("Пришел DELETE запрос /films/{id} с параметром {}", id);
        filmService.deleteFilm(id);
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable Long id) {
        log.info("Пришел GET запрос /films/{id}");
        Film response = filmService.getFilm(id);
        log.info("Отправлен ответ GET /films/{id} с телом: {}", response);
        return response;
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Пришел PUT запрос /{id}/like/{userId} с параметрами {} и {}", id, userId);
        filmService.like(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Пришел DELETE запрос /films/{id} с параметром {}", id);
        filmService.disLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularMovies(@RequestParam(defaultValue = "10") Integer count) {
        log.info("Пришел GET запрос /popular");
        List<Film> response = filmService.getFirstMostPopularFilms(count);
        log.info("Отправлен ответ GET /popular с телом: {}", response);
        return response;
    }

}
