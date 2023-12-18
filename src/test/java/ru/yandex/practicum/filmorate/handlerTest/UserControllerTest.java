package ru.yandex.practicum.filmorate.handlerTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {


    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void addUserWithIncorrectEmailTest() {

        User user = User.builder()
                .id(1L)
                .email("yury.ya.ru")
                .login("yury123")
                .name("Юрий")
                .birthday(LocalDate.now().minusYears(30))
                .build();

        ResponseEntity<User> response = restTemplate.postForEntity("/users", user, User.class);

        assertEquals("400 BAD_REQUEST", response.getStatusCode().toString());
    }

    @Test
    void addUserWithEmptyLoginTest() {

        User user = User.builder()
                .id(1L)
                .email("yury@ya.ru")
                .login(" ")
                .name("Юрий")
                .birthday(LocalDate.now().minusYears(30))
                .build();

        ResponseEntity<User> response = restTemplate.postForEntity("/users", user, User.class);

        assertEquals("400 BAD_REQUEST", response.getStatusCode().toString());
    }

    @Test
    void addUserFromFutureTest() {

        User user = User.builder()
                .id(1L)
                .email("yury@ya.ru")
                .login("yury123")
                .name("Юрий")
                .birthday(LocalDate.now().plusYears(1))
                .build();

        ResponseEntity<User> response = restTemplate.postForEntity("/users", user, User.class);

        assertEquals("400 BAD_REQUEST", response.getStatusCode().toString());
    }

    @Test
    void updateUserWithEmptyLoginTest() {

        User firstUser = User.builder()
                .id(1L)
                .email("yury@ya.ru")
                .login("yury123")
                .name("Юрий")
                .birthday(LocalDate.now().plusYears(1))
                .build();

        ResponseEntity<User> firstResponse = restTemplate.postForEntity("/users", firstUser, User.class);

        User secondUser = User.builder()
                .id(1L)
                .email("yury@ya.ru")
                .login("")
                .name("Юрий")
                .birthday(LocalDate.now().plusYears(1))
                .build();

        HttpEntity<User> entity = new HttpEntity<>(secondUser);
        ResponseEntity<User> secondResponse = restTemplate.exchange("/users", HttpMethod.PUT, entity, User.class);

        assertEquals("400 BAD_REQUEST", secondResponse.getStatusCode().toString());
    }
}