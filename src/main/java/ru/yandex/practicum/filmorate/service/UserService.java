package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    public UserStorage getUserStorage() {
        return userStorage;
    }

    private void validate(User user) {
        if ((user.getName() == null) || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    private void checkUser(Long userId, Long friendId) {
        userStorage.getUser(userId);
        userStorage.getUser(friendId);
    }

    public Collection<User> getUsers() {
        log.info("GET. Пришел  запрос /users на получение списка пользователей");
        Collection<User> response = userStorage.getUsers();
        log.info("GET. Отправлен ответ /users на получение списка пользователей");
        return response;
    }

    public User addUser(User user) {
        log.info("POST. Пришел  запрос /users с телом: {}", user);
        validate(user);
        User response = userStorage.addUser(user);
        log.info("POST. Отправлен ответ /users с телом: {}", user);
        return response;
    }

    public User updateUser(User user) {
        log.info("PUT. Пришел  запрос /users с телом: {}", user);
        validate(user);
        User response = userStorage.updateUser(user);
        log.info("PUT. Отправлен ответ /users с телом: {}", user);
        return response;
    }

    public User getUser(Long id) {
        return userStorage.getUser(id);
    }

    public void deleteUser(Long userId) {
        if (getUser(userId) == null) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден");
        }
        log.info("Удален фильм с id: {}", userId);
        userStorage.deleteUser(userId);
    }

    public void addFriend(Long userId, Long friendId) {
        checkUser(userId, friendId);
        userStorage.addFriend(userId, friendId);
        log.info("'{}' добавил '{}' в список друзей", userId, friendId);
    }

    public List<User> getFriends(Long userId) {
        List<User> result = userStorage.getFriends(userId);
        log.info("друзья пользователя с id = " + userId + result);
        return result;
    }

    public List<User> getMutualFriends(Long user1Id, Long user2Id) {
        checkUser(user1Id, user2Id);
        List<User> result = userStorage.getCommonFriends(user1Id, user2Id);
        log.info("Общие друзья пользователя с id " + " {} и {} {} ", user1Id, user2Id, result);
        return result;
    }

    public void deleteFriend(Long userId, Long friendId) {
        checkUser(userId, friendId);
        userStorage.deleteFriend(userId, friendId);
        log.info("Друг удален");
        log.info("'{}' удален '{}' из списка друзей", userId, friendId);
    }
}