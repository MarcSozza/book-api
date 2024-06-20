package org.books.api.errors;

import org.books.api.models.Book;

public class YearInTheFuture extends CustomErrorException {

    private static final ErrorCode errorCode = ErrorCode.YEAR_IN_THE_FUTURE;

    public YearInTheFuture(Book book) {
        super("L'année de publication est dans le futur: " + book.getYearOfPublication(), errorCode.getCode());
    }
}
