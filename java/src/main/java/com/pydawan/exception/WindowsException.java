package com.pydawan.exception;

public class WindowsException extends RuntimeException {

    private static final long serialVersionUID = 9106106501L;

    public WindowsException() {
        super("Windows error code: " + WindowsError.INSTANCE.GetLastError());
    }
}
