package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    private Long id;
    @NotBlank
    @Email
    private String email;
    @NotBlank(message = "Поле login не может содержать пробелы")
    private String login;
    private String name;
    @PastOrPresent
    private LocalDate birthday;
    @JsonIgnore
    private final Set<Long> friends = new HashSet<>();

    public void addFriend(Long id) {
        friends.add(id);
    }

    public void removeFriend(Long id) {
        friends.remove(id);
    }

    public int getAllFriends() {
        return friends.size();
    }

}