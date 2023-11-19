package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.BadRequestException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Film> getFilms() {
        log.info("GET. Пришел  запрос /films на получение списка фильмов");
        List<Film> response = filmStorage.getFilms();
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

    public Film getFilm(Long id) {
        return filmStorage.getFilm(id);
    }

    public void like(Long filmId, Long userId) {
        Film film = filmStorage.getFilm(filmId);
        userService.getUserStorage().getUser(userId);
        if (film == null) {
            throw new NotFoundException("Фильм с id " + filmId + " не найден");
        }
        film.addLike(userId);
        log.info("Пользователь с id {} поставил like фильму с id {}", userId, filmId);
    }

    public void disLike(Long filmId, Long userId) {
        Film film = filmStorage.getFilm(filmId);
        userService.getUserStorage().getUser(userId);
        if (film == null) {
            throw new NotFoundException("Фильм с id " + filmId + " не найден");
        }
        film.removeLike(userId);
        log.info("Пользователь с id {} убрал like фильму с id {}", userId, filmId);
    }

    public List<Film> getFirstMostPopularFilms(Integer count) {
        log.info("Получение списка из " + count + " популярных фильмов");
        return filmStorage.getFilms()
                .stream()
                .sorted(Comparator.comparingInt(Film::getLikes).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

}