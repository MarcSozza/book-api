package org.books.api.errors;

import org.books.api.models.Book;

public class NotInRangeMaxBook extends CustomErrorException {

    private static final ErrorCode errorCode = ErrorCode.NOT_IN_THE_RANGE_MAX_BOOK;

    public NotInRangeMaxBook(Book book) {
        super("Le nombre de livre pour l'auteur" + book.getAuthor() + " a dépassé la limite autorisé",
              errorCode.getCode());

    }
}
