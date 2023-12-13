package ru.yandex.practicum.filmorate.handlerTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FilmControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void addFilmWithEmptyFieldNameTest() {

        Film film = Film.builder()
                .name(null)
                .description("Описание для фильма")
                .releaseDate(LocalDate.now().minusYears(21))
                .duration(100)
                .build();

        ResponseEntity<Film> response = restTemplate.postForEntity("/films", film, Film.class);

        assertEquals("400 BAD_REQUEST", response.getStatusCode().toString());
    }

    @Test
    void addFilmWithDescriptionBeyondTheLimitTest() {
        String description = "String".repeat(200);

        Film film = Film.builder()
                .name("А зори здесь тихие")
                .description(description)
                .releaseDate(LocalDate.now()
                        .minusYears(21))
                .duration(180).build();

        ResponseEntity<Film> response = restTemplate.postForEntity("/films", film, Film.class);

        assertEquals("400 BAD_REQUEST", response.getStatusCode().toString());
    }

    @Test
    void addFilmWithNegativeDurationFieldTest() {

        Film film = Film.builder()
                .name("Название")
                .description("Описание для фильма")
                .releaseDate(LocalDate.now().minusYears(20))
                .duration(-180)
                .build();

        ResponseEntity<Film> response = restTemplate.postForEntity("/films", film, Film.class);

        assertEquals("400 BAD_REQUEST", response.getStatusCode().toString());
    }

    @Test
    void updateFilmWithEmptyNameTest() {

        Film firstFilm = Film.builder()
                .name("Описание для фильма 10")
                .description("Описание для фильма")
                .releaseDate(LocalDate.now().minusYears(14))
                .duration(180)
                .build();

        ResponseEntity<Film> response = restTemplate.postForEntity("/films", firstFilm, Film.class);

        Film secondField = Film.builder()
                .name(null)
                .description("Описание для фильма")
                .releaseDate(LocalDate.now().minusYears(14))
                .duration(180)
                .build();

        HttpEntity<Film> entity = new HttpEntity<>(secondField);
        ResponseEntity<Film> response2 = restTemplate.exchange("/films", HttpMethod.PUT, entity, Film.class);

        assertEquals("400 BAD_REQUEST", response2.getStatusCode().toString());
    }
}