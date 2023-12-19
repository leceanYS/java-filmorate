package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.UserStorage;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public User addUser(User user) {
        Map<String, Object> keys = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName("users")
                .usingColumns("user_name", "login", "email", "birthday")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKeyHolder(Map.of(
                        "user_name", user.getName(),
                        "login", user.getLogin(),
                        "email", user.getEmail(),
                        "birthday", java.sql.Date.valueOf(user.getBirthday())))
                .getKeys();
        user.setId(((Integer) keys.get("id")).longValue());
        return user;
    }

    @Override
    public List<User> getUsers() {
        String sqlQuery = "SELECT * FROM users";
        SqlRowSet srs = jdbcTemplate.queryForRowSet(sqlQuery);
        List<User> users = new ArrayList<>();
        while (srs.next()) {
            users.add(userMap(srs));
        }
        return users;
    }

    @Override
    public User getUser(Long userId) {
        String sqlQuery = "SELECT * FROM users WHERE id = ?";
        SqlRowSet srs = jdbcTemplate.queryForRowSet(sqlQuery, userId);
        if (srs.next()) {
            return userMap(srs);
        } else {
            throw new NotFoundException("Пользователь с id =" + userId + " не найден");
        }
    }

    @Override
    public User updateUser(User user) {
        getUser(user.getId());
        String sqlQuery = "UPDATE users "
                + "SET user_name = ?, "
                + "login = ?, "
                + "email = ?, "
                + "birthday = ? "
                + "WHERE id = ?";
        jdbcTemplate.update(sqlQuery, user.getName(), user.getLogin(),
                user.getEmail(), user.getBirthday(), user.getId());
        return user;
    }

    @Override
    public void deleteUser(Long userId) {
        String sqlQuery = "DELETE FROM users WHERE id = " + userId;
        jdbcTemplate.update(sqlQuery, Long.valueOf(userId).intValue());
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        String sqlQuery = "INSERT INTO friends (user_id, friend_id, status) "
                + "VALUES(?, ?, ?)";
        jdbcTemplate.update(sqlQuery, userId, friendId, true);
    }

    public void deleteFriend(Long userId, Long friendId) {
        String sqlQuery = "DELETE friends "
                + "WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sqlQuery, userId, friendId);
    }

    public List<User> getFriends(Long userId) {
        List<User> friends = new ArrayList<>();
        String sqlQuery = "SELECT * FROM users "
                + "WHERE users.id IN (SELECT friend_id from friends "
                + "WHERE user_id = ?)";
        SqlRowSet srs = jdbcTemplate.queryForRowSet(sqlQuery, userId);
        while (srs.next()) {
            friends.add(UserDbStorage.userMap(srs));
        }
        return friends;
    }

    public List<User> getCommonFriends(Long friend1, Long friend2) {
        List<User> commonFriends = new ArrayList<>();
        String sqlQuery = "SELECT * FROM users "
                + "WHERE users.id IN (SELECT friend_id from friends "
                + "WHERE user_id IN (?, ?) "
                + "AND friend_id NOT IN (?, ?))";
        SqlRowSet srs = jdbcTemplate.queryForRowSet(sqlQuery, friend1, friend2, friend1, friend2);
        while (srs.next()) {
            commonFriends.add(UserDbStorage.userMap(srs));
        }
        return commonFriends;
    }

    private static User userMap(SqlRowSet srs) {
        Long id = srs.getLong("id");
        String name = srs.getString("user_name");
        String login = srs.getString("login");
        String email = srs.getString("email");
        LocalDate birthday = Objects.requireNonNull(srs.getTimestamp("birthday"))
                .toLocalDateTime().toLocalDate();
        return User.builder()
                .id(id)
                .name(name)
                .login(login)
                .email(email)
                .birthday(birthday)
                .build();
    }
}