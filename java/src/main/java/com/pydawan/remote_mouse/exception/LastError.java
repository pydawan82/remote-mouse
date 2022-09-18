package com.pydawan.remote_mouse.exception;

import com.pydawan.remote_mouse.jni.windows.ErrorLib;

import com.sun.jna.Platform;
import com.sun.jna.LastErrorException;

public final class LastError {

    public static void throwLastError() throws LastErrorException {

        int error = switch (Platform.getOSType()) {
            case Platform.WINDOWS -> ErrorLib.INSTANCE.GetLastError();
            default -> -1;
        };

        throw new LastErrorException(error);
    }
}
