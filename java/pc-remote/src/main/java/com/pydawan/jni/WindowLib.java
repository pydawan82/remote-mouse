package com.pydawan.jni;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinDef.HWND;

import com.sun.jna.platform.win32.WinUser.WNDENUMPROC;
import com.sun.jna.platform.win32.WinUser.WINDOWINFO;

public interface WindowLib extends Library {
    WindowLib INSTANCE = Native.load("user32", WindowLib.class);

    boolean EnumWindows(WNDENUMPROC wndenumproc, LPARAM lParam);

    HWND GetForegroundWindow();

    boolean GetWindowInfo(HWND hWnd, WINDOWINFO pwi);

    int GetWindowTextW(HWND hWnd, char[] lpString, int nMaxCount);

    int GetWindowTextLengthW(HWND hWnd);

    boolean IsWindowVisible(HWND hWnd);

    boolean PostMessageW(HWND hWnd, int Msg, WPARAM wParam, LPARAM lParam);

    boolean SetForegroundWindow(HWND hWnd);

    boolean ShowWindow(HWND hWnd, int nCmdShow);
}
