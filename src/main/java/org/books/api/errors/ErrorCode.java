package org.books.api.errors;

public enum ErrorCode {
    BOOK_ALREADY_EXIST("0x96464"),
    YEAR_IN_THE_FUTURE("0x75481"),
    NOT_EXHAUSTIVE_NUMBER("0x65832"), NOT_IN_THE_RANGE_NUMBER_OF_PAGE("0x46318"), LIMIT_REACHED(
            "0x64852"), NOT_IN_THE_RANGE_MAX_BOOK(
            "0x75756");

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
