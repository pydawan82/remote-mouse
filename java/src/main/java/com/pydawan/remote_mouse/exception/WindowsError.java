package com.pydawan.remote_mouse.exception;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface WindowsError extends Library {
    public static final WindowsError INSTANCE = (WindowsError) Native.load("kernel32", WindowsError.class);

    public int GetLastError();
}
