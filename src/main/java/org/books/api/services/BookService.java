package org.books.api.services;

import org.books.api.errors.BookAlreadyExistException;
import org.books.api.errors.NotInRangeMaxBook;
import org.books.api.errors.NotInRangeNumberOfPages;
import org.books.api.errors.YearInTheFuture;
import org.books.api.models.Book;
import org.books.api.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final BookServiceUtils bookServiceUtils;
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookServiceUtils bookServiceUtils, BookRepository bookRepository) {
        this.bookServiceUtils = bookServiceUtils;
        this.bookRepository = bookRepository;
    }

    public Book createBook(Book book) {
        if (!bookServiceUtils.isPublicationYearInPastOrPresent(book)) {
            throw new YearInTheFuture(book);
        }

        if (!bookServiceUtils.isNumberOfPagesBetweenRange(book)) {
            throw new NotInRangeNumberOfPages(book);
        }

        Long numberOfBooksForAuthor = bookRepository.countBooksByAuthor(book.getAuthor());
        if (bookServiceUtils.isAuthorBookLimitReached(numberOfBooksForAuthor)) {
            throw new NotInRangeMaxBook(book);
        }

        List<Book> books = new ArrayList<>();
        bookRepository.findAll()
                      .forEach(books::add); //reviens à faire .forEach(book -> books.add(book))
        if (bookServiceUtils.isAlreadyKnown(book, books)) {
            throw new BookAlreadyExistException(book);
        } else {
            return bookRepository.save(book);
        }

    }
}
