package org.books.api.controllers;

import org.books.api.errors.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class ErrorHandler {

    @ExceptionHandler(BookAlreadyExistException.class)
    public ResponseEntity<String> handleExceptionBookAlreadyException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(YearInTheFuture.class)
    public ResponseEntity<String> handleExceptionYearInTheFuture(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotExhaustiveNumber.class)
    public ResponseEntity<String> handleExceptionNotExhaustiveYear(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotInRangeNumberOfPages.class)
    public ResponseEntity<String> handleExceptionUnexpectedNumberOfPage(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotInRangeMaxBook.class)
    public ResponseEntity<String> handleExceptionUnexpectedMaxBook(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
