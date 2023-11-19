package ru.yandex.practicum.filmorate.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;

public class UserControllerTest {

    private InMemoryUserStorage storage = new InMemoryUserStorage();
    private UserService service = new UserService(storage);
    private UserController controller = new UserController(service);

    private final User user = User.builder()
            .id(1L)
            .email("testUser@ymain.ru")
            .login("Fox")
            .name("Юрий")
            .birthday(LocalDate.of(1980, 1, 1))
            .build();

    @Test
    void addUserTest() {
        controller.addUser(user);

        Assertions.assertEquals(1, controller.getUsers().size());
    }
}