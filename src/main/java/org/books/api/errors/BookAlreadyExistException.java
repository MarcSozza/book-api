package org.books.api.errors;

import org.books.api.models.Book;

import static org.books.api.errors.ErrorCode.ERROR_CODE_BOOK_ALREADY_EXIST;

public class BookAlreadyExistException extends CustomErrorException {

    private static final ErrorCode errorCode = ERROR_CODE_BOOK_ALREADY_EXIST;

    public BookAlreadyExistException(Book book) {
        super("Book already exists with author:" + book.getAuthor() + " and title:" + book.getTitle(), errorCode.getCode());
    }
}
