package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.BadRequestException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.interfaces.FilmStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserService userService;

    private void validate(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new BadRequestException("Ошибка по минимальной дате релиза фильма," +
                    "releaseDate не может быть меньше 28 декабря 1895 года");
        }
    }

    public Collection<Film> getFilms() {
        log.info("GET. Пришел  запрос /films на получение списка фильмов");
        Collection<Film> response = filmStorage.getFilms();
        log.info("GET. Отправлен ответ /films на получение списка фильмов");
        return response;
    }

    public Film addFilm(Film film) {
        log.info("POST. Пришел  запрос /films с телом: {}", film);
        validate(film);
        Film response = filmStorage.addFilm(film);
        log.info("POST. Отправлен ответ /films с телом: {}", response);
        return response;
    }

    public Film updateFilm(Film film) {
        log.info("PUT. Пришел запрос /films с телом: {}", film);
        validate(film);
        Film response = filmStorage.updateFilm(film);
        log.info("PUT. Отправлен ответ /films с телом: {}", film);
        return response;
    }

    public void deleteFilm(Long filmId) {
        if (getFilm(filmId) == null) {
            throw new NotFoundException("Фильм с id = " + filmId + " не найден");
        }
        log.info("Удален фильм с id: {}", filmId);
        filmStorage.deleteFilm(filmId);
    }

    public Film getFilm(Long id) {
        log.info("Запрошен фильм с id = " + id);
        return filmStorage.getFilm(id);
    }

    public void like(Long filmId, Long userId) {
        Film film = filmStorage.getFilm(filmId);
        if (film != null) {
            if (userService.getUser(userId) != null) {
                filmStorage.like(filmId, userId);
                log.info("Лайк добавлен");
            } else {
                throw new NotFoundException("пользователь с id = " + userId + " не найден");
            }
        } else {
            throw new NotFoundException("Фильм с id  = " + filmId + " не найден");
        }
    }

    public void disLike(Long filmId, Long userId) {
        Film film = filmStorage.getFilm(filmId);
        if (film != null) {
            if (userService.getUser(userId) != null) {
                filmStorage.disLike(filmId, userId);
                log.info("Лайк удален");
            } else {
                throw new NotFoundException("пользователь с id = " + userId + " не найден");
            }
        } else {
            throw new NotFoundException("Фильм с id  " + filmId + " не найден");
        }
    }

    public List<Film> getFirstMostPopularFilms(Integer count) {
        List<Film> result = new ArrayList<>(filmStorage.getPopular(count));
        log.info("Запрос популярного фильма");
        return result;
    }

}