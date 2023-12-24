package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    private void validate(User user) {
        if ((user.getName() == null) || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    private void checkUser(Long userId, Long friendId) {
        userStorage.getUser(userId);
        userStorage.getUser(friendId);
    }

    public List<User> getUsers() {
        log.info("Получение списка всех пользователей из БД");
        List<User> response = userStorage.getUsers();
        log.info("Из БД получено {} объектов", response.size());
        return response;
    }

    public User addUser(User user) {
        log.info("Добавление пользователя в БД");
        validate(user);
        User response = userStorage.addUser(user);
        log.info("Пользователь '{}' успешно добавлен", response.getName());
        return response;
    }

    public User updateUser(User user) {
        log.info("Обновление пользователя с id {}", user.getId());
        validate(user);
        User response = userStorage.updateUser(user);
        log.info("Обновление пользователя с id {} успешно завершено", user.getId());
        return response;
    }

    public User getUser(Long id) {
        log.info("Запрошен пользователь с id = " + id);
        return userStorage.getUser(id);
    }

    public void deleteUser(Long userId) {
        log.info("Удален пользователь с id: {}", userId);
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