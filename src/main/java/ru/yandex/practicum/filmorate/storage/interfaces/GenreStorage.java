package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreStorage {

    void deleteGenre(Long filmId);

    Genre getGenre(Long genreId);

    List<Genre> getGenres();
}