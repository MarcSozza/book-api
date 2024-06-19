package org.books.api.errors;

import org.books.api.models.Book;

public class YearInTheFuture extends RuntimeException {

    public YearInTheFuture(Book book) {
        super("L'année de publication est dans le futur: " + book.getYearOfPublication());
    }
}
