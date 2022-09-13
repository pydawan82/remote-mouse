package com.pydawan.winuser;

public class WinUserException extends RuntimeException {

    public WinUserException() {
        super();
    }

    public WinUserException(String message) {
        super(message);
    }

    public WinUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public WinUserException(Throwable cause) {
        super(cause);
    }

}
