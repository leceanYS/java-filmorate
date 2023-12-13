package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage extends FriendsStorage {

    User addUser(User user);

    Collection<User> getUsers();

    User getUser(Long id);

    User updateUser(User user);

    String deleteUser(Long id);
}