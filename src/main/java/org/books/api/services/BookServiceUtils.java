package org.books.api.services;

import org.books.api.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service
public class BookServiceUtils {

    private static final int MAXIMUM = 9999;
    private static final int MINIMUM = 10;
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

        return utilsService.isPublicationYearInPastOrPresent(yearToCheck, curentYear);
    }

    public boolean isBetweenRangeAuthorized(Book bookToAdd) {
        return bookToAdd.getPageCount() >= BookServiceUtils.MINIMUM && bookToAdd.getPageCount() <= BookServiceUtils.MAXIMUM;
    }
}
