package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.Set;

public interface FilmStorage extends LikesStorage {

    Film addFilm(Film film);

    Collection<Film> getFilms();

    Film getFilm(Long id);

    Film updateFilm(Film film);

    void addGenre(Long filmId, Set<Genre> genres);

    String deleteFilm(Long id);

}
