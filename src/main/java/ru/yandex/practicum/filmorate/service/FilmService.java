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
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserService userService;

    private static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    private void validate(Film film) {
        if (film.getReleaseDate().isBefore(MIN_RELEASE_DATE)) {
            throw new BadRequestException("Ошибка по минимальной дате релиза фильма," +
                    "releaseDate не может быть меньше 28 декабря 1895 года");
        }
    }

    public List<Film> getFilms() {
        log.info("Получение списка всех фильмов из БД");
        List<Film> response = filmStorage.getFilms();
        log.info("Из БД получено {} объектов", response.size());
        return response;
    }

    public Film addFilm(Film film) {
        log.info("Добавление фильма в БД");
        validate(film);
        Film response = filmStorage.addFilm(film);
        log.info("Фильм '{}' успешно добавлен", response.getName());
        return response;
    }

    public Film updateFilm(Film film) {
        log.info("Обновление фильма с id {}", film.getId());
        validate(film);
        Film response = filmStorage.updateFilm(film);
        log.info("Обновление фильма с id {} успешно завершено", film.getId());
        return response;
    }

    public void deleteFilm(Long filmId) {
        log.info("Поиск фильма в БД с id {}", filmId);
        filmStorage.deleteFilm(filmId);
        log.info("Фильм с id {} найден", filmId);
        log.info("Фильм с id {} успешно удален", filmId);
    }

    public Film getFilm(Long id) {
        log.info("Запрошен фильм с id = " + id);
        return filmStorage.getFilm(id);
    }

    public void like(Long filmId, Long userId) {
        if (userService.getUser(userId) == null) {
            throw new NotFoundException("пользователь с id = " + userId + " не найден");
        }
        filmStorage.like(filmId, userId);
        log.info("Лайк фильму: {} от пользователя: {} добавлен", filmId, userId);
    }

    public void disLike(Long filmId, Long userId) {
        log.info("удаление лайка фильму с id {} пользователем {}", filmId, userId);
        if (filmStorage.getFilm(filmId) == null) {
            throw new NotFoundException("Фильм с id  " + filmId + " не найден");
        }
        if (userService.getUser(userId) == null) {
            throw new NotFoundException("пользователь с id = " + userId + " не найден");
        }
        filmStorage.disLike(filmId, userId);
        log.info("Дизлайк фильму: {} от пользователя: {} добавлен", filmId, userId);
    }

    public List<Film> getFirstMostPopularFilms(Integer count) {
        List<Film> result = new ArrayList<>(filmStorage.getPopular(count));
        log.info("Запрос популярного фильма");
        return result;
    }

}
