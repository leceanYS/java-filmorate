package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();
    private Long id = 0L;

    private Long generateId() {
        return ++id;
    }

    @Override
    public User addUser(User user) {
        user.setId(generateId());
        users.put(user.getId(), user);
        log.info("Пользователь '{}' добавлен в хранилище с id '{}'", user.getName(), user.getId());
        return user;
    }

    @Override
    public List<User> getUsers() {
        log.info("Всего пользователей '{}'", users.size());
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUser(Long id) {
        log.info("Поиск пользователя с id '{}'", users.size());
        if (!users.containsKey(id)) {
            throw new NotFoundException("Пользователя с id " + id + " не найдено");
        }
        return users.get(id);
    }

    @Override
    public User updateUser(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.info("Пользователь '{}' с id '{}' обновлен", user.getName(), user.getId());
            return user;
        } else {
            throw new NotFoundException("Пользователя с id " + user.getId() + " не найдено");
        }
    }

    @Override
    public void deleteUsers() {
        users.clear();
        log.info("Все пользователи удалены");
    }

}