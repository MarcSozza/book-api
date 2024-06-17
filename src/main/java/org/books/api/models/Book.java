package org.books.api.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String title;

    private String author;

    @Column(name = "year_of_publication")
    private String yearOfPublication;

    @Column(name = "book_genre")
    private String genre;

    private String summary;

    @Column(name = "page_count")
    private int pageCount;

    public Book(String title, String author, String yearOfPublication, String genre, String summary, int pageCount) {
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
        this.genre = genre;
        this.summary = summary;
        this.pageCount = pageCount;
    }
}
