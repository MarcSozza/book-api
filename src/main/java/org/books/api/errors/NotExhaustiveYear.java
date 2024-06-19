package org.books.api.errors;

public class NotExhaustiveYear extends CustomErrorException {

    private static final ErrorCode errorCode = ErrorCode.ERROR_CODE_NOT_EXHAUSTIVE_YEAR;

    public NotExhaustiveYear(String year) {
        super("L'année n'est pas une valeur possible. Valeur envoyée: " + year, errorCode.getCode());
    }
}
