package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface LikesStorage {

    void like(Long filmId, Long userId);

    void disLike(Long filmId, Long userId);

    List<Film> getPopular(Integer count);
}