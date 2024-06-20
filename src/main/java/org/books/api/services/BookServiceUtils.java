package org.books.api.services;

import org.books.api.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service
public class BookServiceUtils {

    private static final int MAXIMUM_PAGES = 9999;
    private static final int MINIMUM_PAGES = 10;
    private static final int MAXIMUM_AUTHOR_BOOKS = 50;
    private UtilsService utilsService;

    @Autowired
    public BookServiceUtils(UtilsService utilsService) {
        this.utilsService = utilsService;
    }

    public boolean isAlreadyKnown(Book bookToAdd, List<Book> books) {
        String author = bookToAdd.getAuthor();
        String title = bookToAdd.getTitle();

        for (Book book : books) {
            if (utilsService.isStringEqualIgnoreCase(book.getTitle(), title) && utilsService.isStringEqualIgnoreCase(
                    book.getAuthor(), author)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPublicationYearInPastOrPresent(Book bookToAdd) {
        String curentYear = String.valueOf(Year.now()
                                               .getValue());
        String yearToCheck = bookToAdd.getYearOfPublication();
        return utilsService.isFirstLessOrEqual(yearToCheck, curentYear);
    }

    public boolean isNumberOfPagesBetweenRange(Book bookToAdd) {
        return bookToAdd.getPageCount() >= BookServiceUtils.MINIMUM_PAGES && bookToAdd.getPageCount() <= BookServiceUtils.MAXIMUM_PAGES;
    }

    public boolean isAuthorBookLimitReached(Long numberOfBooks) {
        return numberOfBooks >= BookServiceUtils.MAXIMUM_AUTHOR_BOOKS;
    }
}
