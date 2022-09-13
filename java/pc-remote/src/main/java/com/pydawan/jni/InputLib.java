package com.pydawan.jni;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import com.sun.jna.platform.win32.WinDef.DWORD;

import com.sun.jna.platform.win32.WinUser.INPUT;

public interface InputLib extends Library {
    public static final InputLib INSTANCE = (InputLib) Native.load("user32", InputLib.class);

    int SendInput(int nInputs, INPUT[] pInputs, int cbSize);
}