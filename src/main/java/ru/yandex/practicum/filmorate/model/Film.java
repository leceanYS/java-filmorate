package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class Film {

    private Long id;
    @NotBlank
    private String name;
    @Size(max = 200)
    @NotBlank
    private String description;
    @NotNull
    private LocalDate releaseDate;
    @Positive
    private int duration;
    private Set<Genre> genres;
    @NotNull
    private RatingMpa mpa;
    @NotNull
    @JsonIgnore
    private final Set<Long> likes;


    public void addLike(Long userId) {
        likes.add(userId);
    }

    public void removeLike(Long userId) {
        likes.remove(userId);
    }

    public int getLikes() {
        return likes.size();
    }

    public void addGenre(Genre genre) {
        genres.add(genre);
    }

    public void deleteGenres() {
        genres.clear();
    }

    public void deleteGenre(Genre genre) {
        genres.remove(genre);
    }

}
