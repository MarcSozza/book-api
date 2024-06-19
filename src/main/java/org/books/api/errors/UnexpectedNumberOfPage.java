package org.books.api.errors;

import org.books.api.models.Book;

public class UnexpectedNumberOfPage extends CustomErrorException {
    private static final ErrorCode errorCode = ErrorCode.ERROR_CODE_UNEXPECTED_NUMBER_OF_PAGE;

    public UnexpectedNumberOfPage(Book book) {
        super("Le nombre de page n'est pas valide: " + book.getPageCount(), errorCode.getCode());
    }
}
