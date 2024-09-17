package org.books.api.repositories;

import org.books.api.enums.Genre;
import org.books.api.models.GenreEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class GenreLoader {

    @Autowired
    private GenreRepository genreRepository;

    public void loadGenres() {
        Arrays.stream(Genre.values()).forEach(genreEnum -> {
            if (!genreRepository.existsById(genreEnum.ordinal())) {
                GenreEntity genreEntity = new GenreEntity();
                genreEntity.setId(genreEnum.ordinal());
                genreEntity.setName(genreEnum.name());
                genreRepository.save(genreEntity);
            }
        });
    }
}