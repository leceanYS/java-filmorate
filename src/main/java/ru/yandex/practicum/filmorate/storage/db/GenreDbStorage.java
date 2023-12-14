package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.interfaces.GenreStorage;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void deleteGenre(Long filmId) {
        String sglQuery = "DELETE film_genres WHERE genre_id = ?";
        jdbcTemplate.update(sglQuery, Long.valueOf(filmId).intValue());
    }

    @Override
    public Genre getGenre(Long genreId) {
        String sqlQuery = "SELECT * FROM genres WHERE id = ?";
        SqlRowSet srs = jdbcTemplate.queryForRowSet(sqlQuery, Long.valueOf(genreId).intValue());
        if (srs.next()) {
            return new Genre(genreId, srs.getString("genre_name"));
        }
        return null;
    }

    @Override
    public List<Genre> getGenres() {
        List<Genre> genres = new ArrayList<>();
        String sqlQuery = "SELECT * FROM genres ";
        SqlRowSet srs = jdbcTemplate.queryForRowSet(sqlQuery);
        while (srs.next()) {
            genres.add(new Genre(srs.getLong("id"), srs.getString("genre_name")));
        }
        return genres;
    }
}