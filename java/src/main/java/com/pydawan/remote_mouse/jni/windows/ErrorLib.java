package com.pydawan.remote_mouse.jni.windows;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface ErrorLib extends Library {
    public static final ErrorLib INSTANCE = (ErrorLib) Native.load("kernel32", ErrorLib.class);

    public int GetLastError();
}
