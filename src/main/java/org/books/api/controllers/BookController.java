package org.books.api.controllers;

import org.books.api.errors.BookAlreadyExistException;
import org.books.api.errors.NotExhaustiveYear;
import org.books.api.errors.YearInTheFuture;
import org.books.api.models.Book;
import org.books.api.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(final BookService service) {
        this.bookService = service;
    }

    @PostMapping("/book")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return new ResponseEntity<>(bookService.createBook(book), HttpStatus.CREATED);
    }

    @ExceptionHandler(BookAlreadyExistException.class)
    public ResponseEntity<String> handleExceptionBookAlreadyException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(YearInTheFuture.class)
    public ResponseEntity<String> handleExceptionYearInTheFuture(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotExhaustiveYear.class)
    public ResponseEntity<String> handleExceptionNotExhaustiveYear(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
