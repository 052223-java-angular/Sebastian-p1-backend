package com.revature.PureDataBase2.util.custom_exceptions;

public class LibraryNotFoundException extends RuntimeException {
    public LibraryNotFoundException(String message) {
        super(message);
    }
}
