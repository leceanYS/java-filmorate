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

    private Genre mapRowToGenre(SqlRowSet rowSet) {
        Long genreId = rowSet.getLong("id");
        String genreName = rowSet.getString("genre_name");
        return new Genre(genreId, genreName);
    }

    @Override
    public void deleteGenre(Long filmId) {
        log.info("Deleting genre from film with ID: {}", filmId);
        String sqlQuery = "DELETE film_genres WHERE genre_id = ?";
        jdbcTemplate.update(sqlQuery, Long.valueOf(filmId).intValue());
    }

    @Override
    public Genre getGenre(Long genreId) {
        log.info("Getting genre with ID: {}", genreId);
        String sqlQuery = "SELECT * FROM genres WHERE id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlQuery, Long.valueOf(genreId).intValue());
        if (rowSet.next()) {
            return mapRowToGenre(rowSet);
        }
        return null;
    }

    @Override
    public List<Genre> getGenres() {
        log.info("Getting all genres");
        List<Genre> genres = new ArrayList<>();
        String sqlQuery = "SELECT * FROM genres ";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlQuery);
        while (rowSet.next()) {
            genres.add(mapRowToGenre(rowSet));
        }
        return genres;
    }
}