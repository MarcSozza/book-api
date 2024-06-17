package org.books.api.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    public String title;

    public String author;

    @Column(name = "year_of_publication")
    public String yearOfPublication;

    @Column(name = "book_genre")
    public String genre;

    public String summary;

    @Column(name = "page_count")
    public int pageCount;
}
