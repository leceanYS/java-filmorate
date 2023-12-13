package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.RatingMpa;
import ru.yandex.practicum.filmorate.service.RatingMpaService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mpa")
public class RatingMpaController {

    private final RatingMpaService ratingMpaService;

    @GetMapping
    public List<RatingMpa> getMpaRatings() {
        log.info("GET. Запрос на получение списка рейтингов");
        return ratingMpaService.getMpaRatings();
    }

    @GetMapping("/{id}")
    public RatingMpa getMpaRating(@PathVariable Integer id) {
        log.info("GET. Запрос на получение рейтинга с id {}", id);
        return ratingMpaService.getMpaRating(id);
    }
}