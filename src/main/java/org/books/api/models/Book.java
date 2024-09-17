package org.books.api.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.books.api.enums.Genre;

@Data
@Entity
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String author;

    @Column(name = "year_of_publication")
    private String yearOfPublication;

    @Enumerated(EnumType.STRING)
    @Column(name = "book_genre")
    @Schema(description = "Genre of the book", example = "FICTION", allowableValues = {"FICTION", "NON_FICTION", "SCIFI", "FANTASY", "BIOGRAPHY"})
    private Genre genre;

    private String summary;

    @Column(name = "page_count")
    private int pageCount;

    public Book(String title, String author, String yearOfPublication, Genre genre, String summary, int pageCount) {
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
        this.genre = genre;
        this.summary = summary;
        this.pageCount = pageCount;
    }

}
