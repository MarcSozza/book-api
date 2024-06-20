package org.books.api.errors;

public class NotExhaustiveNumber extends CustomErrorException {

    private static final ErrorCode errorCode = ErrorCode.NOT_EXHAUSTIVE_NUMBER;

    public NotExhaustiveNumber(String year) {
        super("Erreur dans la valeur envoyée: " + year, errorCode.getCode());
    }
}
