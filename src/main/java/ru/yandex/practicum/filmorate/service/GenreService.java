package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.interfaces.GenreStorage;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreService {


    private final GenreStorage genreStorage;

    public Genre getGenre(Long id) {
        Genre genre = genreStorage.getGenre(id);
        if (genre == null) {
            throw new NotFoundException("Жанр не найден");
        }
        return genre;
    }

    public List<Genre> getGenres() {
        return genreStorage.getGenres();
    }
}