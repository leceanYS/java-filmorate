package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {


    public final FilmService filmService;

    @GetMapping
    public List<Film> getFilms() {
        return filmService.getFilms();
    }

    @PostMapping
    public Film addFilm(@RequestBody @Valid Film film) {
        return filmService.addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid Film film) {
        return filmService.updateFilm(film);
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable Long id) {
        return filmService.getFilm(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.like(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.disLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularMovies(@RequestParam(defaultValue = "10") @Min(1) Integer count) {
        return filmService.getFirstMostPopularFilms(count);
    }
}