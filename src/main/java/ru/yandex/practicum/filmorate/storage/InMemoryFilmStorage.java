package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();
    private Long id = 0L;

    private Long generateId() {
        return ++id;
    }

    @Override
    public Film addFilm(Film film) {
        film.setId(generateId());
        films.put(film.getId(), film);
        log.info("Фильм '{}' добавлен в хранилище с id '{}'", film.getName(), film.getId());
        return film;
    }

    @Override
    public List<Film> getFilms() {
        log.info("В хранилище '{}' фильмов", films.size());
        return new ArrayList<>(films.values());
    }

    @Override
    public Film getFilm(Long id) {
        log.info("Из хранилища запрошен фильм с id '{}'", films.size());
        if (!films.containsKey(id)) {
            throw new NotFoundException("В хранилище нет фильма с id " + id);
        }
        return films.get(id);
    }

    @Override
    public Film updateFilm(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("Фильм '{}' с id '{}' обновлен", film.getName(), film.getId());
            return film;
        } else {
            throw new NotFoundException("В хранилище нет фильма с id " + film.getId());
        }
    }

    @Override
    public void deleteFilms() {
        films.clear();
        log.info("Хранилище фильмов очищено");
    }

}