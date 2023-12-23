package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.RatingMpa;
import ru.yandex.practicum.filmorate.storage.db.RatingMpaDbStorage;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RatingMpaService {
    private final RatingMpaDbStorage ratingMpaStorage;

    public RatingMpa getMpaRating(int id) {
        RatingMpa mpa = ratingMpaStorage.getMpaRating(id);
        if (mpa == null) {
            throw new NotFoundException("Рейтинг не найден");
        }
        return mpa;
    }

    public List<RatingMpa> getMpaRatings() {
        return ratingMpaStorage.getMpaRatings();
    }
}

