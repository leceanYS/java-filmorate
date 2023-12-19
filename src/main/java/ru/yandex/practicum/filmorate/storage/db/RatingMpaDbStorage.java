package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.RatingMpa;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RatingMpaDbStorage {

    private final JdbcTemplate jdbcTemplate;

    private RatingMpa mapRowToRatingMpa(SqlRowSet rowSet) {
        Long ratingId = rowSet.getLong("mpa_id");
        String ratingName = rowSet.getString("rating_name");

        return new RatingMpa(ratingId, ratingName);
    }

    public RatingMpa getMpaRating(int ratingId) {
        String sqlQuery = "SELECT * FROM rating_mpa WHERE mpa_id = ?";
        SqlRowSet srs = jdbcTemplate.queryForRowSet(sqlQuery, ratingId);
        if (srs.next()) {
            return mapRowToRatingMpa(srs);
        }
        return null;
    }

    public List<RatingMpa> getMpaRatings() {
        log.info("Fetching all MPA ratings from database");
        List<RatingMpa> ratingsMpa = new ArrayList<>();
        String sqlQuery = "SELECT * FROM rating_mpa";
        SqlRowSet srs = jdbcTemplate.queryForRowSet(sqlQuery);
        while (srs.next()) {
            ratingsMpa.add(mapRowToRatingMpa(srs));
        }
        return ratingsMpa;
    }
}