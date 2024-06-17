package org.books.api.services;

import org.books.api.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceUtils {

    @Autowired
    private UtilsService utilsService;

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

}
