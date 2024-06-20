package org.books.api.errors;

import org.books.api.models.Book;

public class NotInRangeNumberOfPages extends CustomErrorException {
    private static final ErrorCode errorCode = ErrorCode.NOT_IN_THE_RANGE_NUMBER_OF_PAGE;

    public NotInRangeNumberOfPages(Book book) {
        super("Le nombre n'est pas dans la limite autorisée: " + book.getPageCount(), errorCode.getCode());
    }
}
