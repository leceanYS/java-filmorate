package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.db.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.db.RatingMpaDbStorage;
import ru.yandex.practicum.filmorate.storage.db.UserDbStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class FilmorateApplicationTests {

    private final FilmDbStorage filmDbStorage;
    private final RatingMpaDbStorage ratingMpaDbStorage;
    private final UserDbStorage userDbStorage;

    @Test
    void getFilmsFromStorageTest() {

        Film firstFilm = Film.builder()
                .id(1L)
                .name("Название фильма 1")
                .description("Описание для фильма 1")
                .releaseDate(LocalDate.now().minusYears(40))
                .duration(180)
                .genres(new HashSet<>())
                .mpa(ratingMpaDbStorage.getMpaRating(1)).build();

        Film secondFilm = Film.builder()
                .id(1L)
                .name("Название фильма 2")
                .description("Описание для фильма 2")
                .releaseDate(LocalDate.now().minusYears(40))
                .duration(180)
                .genres(new HashSet<>())
                .mpa(ratingMpaDbStorage.getMpaRating(1)).build();

        filmDbStorage.addFilm(firstFilm);
        filmDbStorage.addFilm(secondFilm);
        Collection<Film> films = filmDbStorage.getFilms();

        assertThat(films).hasSize(2);
    }

    @Test
    void addFilmToStorageTest() {

        Film film = Film.builder()
                .id(1L)
                .name("Название фильма 3")
                .description("Описание для фильма 3")
                .releaseDate(LocalDate.now().minusYears(40))
                .duration(180)
                .genres(new HashSet<>())
                .mpa(ratingMpaDbStorage.getMpaRating(1)).build();

        filmDbStorage.addFilm(film);
        Film filmOptional = filmDbStorage.getFilm(1L);

        assertEquals(filmOptional.getId(), 1);
    }

    @Test
    void getFilmFromStorageTest() {

        Film film = Film.builder()
                .id(1L)
                .name("Название фильма 4")
                .description("Описание для фильма 4")
                .releaseDate(LocalDate.now().minusYears(40))
                .duration(180)
                .genres(new HashSet<>())
                .mpa(ratingMpaDbStorage.getMpaRating(1)).build();

        filmDbStorage.addFilm(film);

        assertEquals(filmDbStorage.getFilm(1L).getId(),film.getId());
    }

    @Test
    public void getUsersFromStorageTest() {

        User firstUser = User.builder()
                .id(1L)
                .name("Иван")
                .login("ivan")
                .email("ivan@yandex.ru")
                .birthday(LocalDate.now().minusYears(50))
                .build();

        User secondUser = User.builder()
                .id(2L)
                .name("Фома")
                .login("Fom")
                .email("foma@ya.ru")
                .birthday(LocalDate.now().minusYears(20))
                .build();

        userDbStorage.addUser(firstUser);
        userDbStorage.addUser(secondUser);
        Collection<User> users = userDbStorage.getUsers();

        assertThat(users).contains(firstUser);
        assertThat(users).contains(secondUser);
    }

    @Test
    public void addUserToStorageTest() {

        User user = User.builder()
                .id(1L)
                .name("Николай")
                .login("nikolay")
                .email("nikolay@yandex.ru")
                .birthday(LocalDate.now().minusYears(50))
                .build();

        userDbStorage.addUser(user);
        User userOptional = userDbStorage.getUser(1L);

        assertEquals(userOptional.getId(), 1);
    }

    @Test
    public void getUserFromStorageTest() {

        User user = User.builder()
                .id(1L)
                .name("Виктор")
                .login("Victor")
                .email("victor@yandex.ru")
                .birthday(LocalDate.now().minusYears(50))
                .build();

        userDbStorage.addUser(user);
        User userOptional = userDbStorage.getUser(1L);

        assertEquals(userOptional.getName(), "Виктор");
    }

}