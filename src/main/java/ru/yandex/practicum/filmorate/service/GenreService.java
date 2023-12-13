package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.db.GenreDbStorage;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreDbStorage genreDbStorage;

    public Genre getGenre(Long id) {
        Genre genre = genreDbStorage.getGenre(id);
        if (genre == null) {
            throw new NotFoundException("Жанр не найден");
        }
        return genre;
    }

    public List<Genre> getGenres() {
        return genreDbStorage.getGenres();
    }
}