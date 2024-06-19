package org.books.api.errors;

import org.books.api.models.Book;

public class BookAlreadyExistException extends RuntimeException{
    public BookAlreadyExistException(Book book){
        super("Book already exists with author:" + book.getAuthor() + " and title:" + book.getTitle());
    }
}
