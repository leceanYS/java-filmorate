package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping
    public List<User> getUsers() {
        log.info("Пришел GET запрос /users");
        List<User> response = userService.getUsers();
        log.info("Отправлен ответ GET /films с телом: {}", response);
        return response;
    }

    @PostMapping
    public User addUser(@RequestBody @Valid User user) {
        log.info("Пришел POST запрос /users с телом {}", user);
        User response = userService.addUser(user);
        log.info("Отправлен ответ POST /users с телом: {}", response);
        return response;
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User user) throws NotFoundException {
        log.info("Пришел PUT запрос /users с телом {}", user);
        User response = userService.updateUser(user);
        log.info("Отправлен ответ PUT /users с телом: {}", response);
        return response;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        log.info("Пришел GET запрос /users/{id}");
        User response = userService.getUser(id);
        log.info("Отправлен ответ GET /users/{id} с телом: {}", response);
        return response;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        log.info("Пришел DELETE запрос /users/{id} с параметром {}", id);
        userService.deleteUser(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("Пришел PUT запрос users/{id}/friends/{friendId} с параметрами {} и {}", id, friendId);
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("Пришел DELETE запрос users/{id}/friends/{friendId} с параметрами {} и {}", id, friendId);
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable Long id) {
        log.info("Пришел GET запрос /users/{id}/friends");
        List<User> response = userService.getFriends(id);
        log.info("Отправлен ответ GET /users/{id}/friends с телом: {}", response);
        return response;
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
        log.info("Пришел GET запрос /users/{id}/friends/common/{otherId}");
        List<User> response = userService.getMutualFriends(id, otherId);
        log.info("Отправлен ответ GET /users/{id}/friends/common/{otherId} с телом: {}", response);
        return response;
    }
}
