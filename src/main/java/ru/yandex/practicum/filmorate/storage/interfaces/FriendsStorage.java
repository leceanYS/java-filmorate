package ru.yandex.practicum.filmorate.storage.interfaces;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendsStorage {

    void addFriend(Long userID, Long friendId);

    void deleteFriend(Long userID, Long friendId);

    List<User> getFriends(Long userId);

    List<User> getCommonFriends(Long friend1, Long friend2);

}