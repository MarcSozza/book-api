package org.books.api.errors;

public class CustomErrorException extends RuntimeException {

    private final String errorCode;

    public CustomErrorException(String message, String errorCode) {
        super("Error Code " + errorCode + ": " + message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }
}
