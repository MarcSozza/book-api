package org.books.api.errors;

public enum ErrorCode {
    ERROR_CODE_BOOK_ALREADY_EXIST("0x96464"),
    ERROR_CODE_YEAR_IN_THE_FUTURE("0x75481"),
    ERROR_CODE_NOT_EXHAUSTIVE_YEAR("0x65832");

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
