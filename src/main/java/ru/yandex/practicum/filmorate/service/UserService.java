package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    public UserStorage getUserStorage() {
        return userStorage;
    }

    // public UserService(UserStorage userStorage) { this.userStorage = userStorage; } вариант замены но возникают проблемы
    private void validate(User user) {
        if ((user.getName() == null) || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    public List<User> getUsers() {
        log.info("GET. Пришел  запрос /users на получение списка пользователей");
        List<User> response = userStorage.getUsers();
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

    public void addFriend(Long userId, Long friendId) {
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);
        user.addFriend(friendId);
        friend.addFriend(userId);
        log.info("'{}' добавил '{}' в список друзей", userId, friendId);
    }

    public List<User> getFriends(Long userId) {
        User user = userStorage.getUser(userId);
        Set<Long> friends = user.getFriends();
        if (friends.isEmpty()) {
            throw new NotFoundException("Список друзей пользователя с id '" + userId + "' пуст");
        }
        return friends.stream()
                .map(userStorage::getUser)
                .collect(Collectors.toList());
    }

    public List<User> getMutualFriends(Long userId, Long friendId) {
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);
        Set<Long> userFriends = user.getFriends();
        Set<Long> friendFriends = friend.getFriends();
        log.info("'{}' запросил список общих друзей '{}'", userId, friendId);
        if (userFriends.stream().anyMatch(friendFriends::contains)) {
            return userFriends.stream()
                    .filter(friendFriends::contains)
                    .map(userStorage::getUser).collect(Collectors.toList());
        } else {
            return List.of();
        }
    }

    public void removeFriend(Long userId, Long friendId) {
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);
        user.removeFriend(friendId);
        friend.removeFriend(userId);
        log.info("'{}' удален '{}' из списка друзей", userId, friendId);
    }


}