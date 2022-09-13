package com.pydawan.winuser;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import lombok.Getter;

import com.sun.jna.Pointer;

import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.RECT;

import com.sun.jna.platform.win32.WinUser.WNDENUMPROC;
import com.sun.jna.platform.win32.WinUser.WINDOWINFO;

import com.pydawan.jni.WindowLib;
import com.pydawan.vk.VirtualKey;

public class Window {

    @Getter
    private HWND hWnd;

    protected Window(HWND hWnd) {
        this.hWnd = hWnd;
    }

    public static Window foreground() {
        return new Window(WindowLib.INSTANCE.GetForegroundWindow());
    }

    /**
     * Enumerates all windows until the predicate returns false
     * or all windows have been enumerated.
     * 
     * @param predicate The predicate to test each window with.
     * @
     */
    public static void enumerate(Predicate<Window> predicate) {
        WNDENUMPROC fn = new WNDENUMPROC() {
            public boolean callback(HWND hWnd, Pointer arg1) {
                return predicate.test(new Window(hWnd));
            }
        };

        boolean result = WindowLib.INSTANCE.EnumWindows(fn, new LPARAM());
        if (!result)
            throw new WinUserException();
    }

    public static List<Window> all() {
        List<Window> windows = new ArrayList<>();
        enumerate(windows::add);
        return windows;
    }

    /**
     * Collects all windows that match the predicate.
     * 
     * @param predicate The predicate to test each window with.
     * @return A list of all windows that match the predicate.
     */
    public static List<Window> collect(Predicate<Window> predicate) {
        List<Window> windows = new ArrayList<>();

        enumerate(w -> {
            if (predicate.test(w)) {
                windows.add(w);
            }

            return true;
        });

        return windows;
    }

    public int getTitleLength() {
        return WindowLib.INSTANCE.GetWindowTextLengthW(hWnd);
    }

    /**
     * Gets the title of the window.
     * 
     * @return The title of the window.
     */
    public String getTitle() {
        int length = getTitleLength();
        if (length == 0)
            return "";

        // Not optimal, but this is Java, not C.
        char[] buffer = new char[length + 1];
        int result = WindowLib.INSTANCE.GetWindowTextW(hWnd, buffer, length + 1);
        if (result == 0)
            throw new WinUserException();

        return new String(buffer);
    }

    /**
     * Gets the window info of the window.
     * 
     * @return The window info of the window.
     */
    public WINDOWINFO getInfo() {
        WINDOWINFO info = new WINDOWINFO();
        boolean result = WindowLib.INSTANCE.GetWindowInfo(hWnd, info);
        if (!result)
            throw new WinUserException();

        return info;
    }

    /**
     * Gets the rectangle of the window.
     */
    public RECT getRect() {
        WINDOWINFO info = getInfo();
        return info.rcWindow;
    }

    @Override
    public String toString() {
        RECT rect = getRect();

        int width = rect.right - rect.left;
        int height = rect.bottom - rect.top;

        return String.format("Window[title=%s, pos=%sx%s, dim=%sx%s]", getTitle(), rect.left, rect.top, width, height);
    }

    /**
     * 
     * @return <code>true</code> if the window is visible, <code>false</code>
     *         otherwise.
     */
    public boolean isVisible() {
        return WindowLib.INSTANCE.IsWindowVisible(hWnd);
    }

    /**
     * A predicate that determines if the window is a main window.
     * This is abritrary and based on three criteria:
     * <ul>
     * <li>The window is visible</li>
     * <li>The window is not an overlapped window (a.k.a. WS_OVERLAPPEDWINDOW)</li>
     * <li>The window has a title</li>
     * </ul>
     * 
     * @return <code>true</code> if the window is a main window, <code>false</code>
     *         otherwise.
     */
    public boolean isMainWindow() {
        boolean isVisible = isVisible();
        WINDOWINFO info = getInfo();
        int titleLength = getTitleLength();

        return isVisible && (info.dwStyle & WindowStyles.WS_OVERLAPPEDWINDOW) != 0 && titleLength > 0;
    }

    /**
     * Set the window to the foreground.
     */
    public void setForeground() {
        boolean result = WindowLib.INSTANCE.SetForegroundWindow(hWnd);
        if (!result)
            throw new WinUserException();
    }

    /**
     * Maximizes the window.
     */
    public void maximize() {
        boolean result = WindowLib.INSTANCE.ShowWindow(hWnd, ShowWindow.SW_MAXIMIZE);
        if (!result)
            throw new WinUserException();
    }

    /**
     * Minimizes the window.
     */
    public void minimize() {
        boolean result = WindowLib.INSTANCE.ShowWindow(hWnd, ShowWindow.SW_MINIMIZE);
        if (!result)
            throw new WinUserException();
    }

    /**
     * Restores the window to its previous state.
     */
    public void restore() {
        boolean result = WindowLib.INSTANCE.ShowWindow(hWnd, ShowWindow.SW_RESTORE);
        if (!result)
            throw new WinUserException();
    }

    /**
     * Sends a message to close the window.
     */
    public void close() {
        boolean result = WindowLib.INSTANCE.PostMessageW(hWnd, WindowMessages.WM_CLOSE, new WPARAM(), new LPARAM());
        if (!result)
            throw new WinUserException();
    }

    /**
     * Sends a key press to the window.
     * 
     * @param key The key to press.
     */
    public void sendKeyDown(VirtualKey key) {
        boolean result = WindowLib.INSTANCE.PostMessageW(hWnd, WindowMessages.WM_KEYDOWN, new WPARAM(key.getValue()),
                new LPARAM());
        if (!result)
            throw new WinUserException();
    }

    /**
     * Sends a key up message to the window.
     * Unknown behavior for now.
     * 
     * @param key The key to send.
     */
    public void sendKeyUp(VirtualKey key) {
        boolean result = WindowLib.INSTANCE.PostMessageW(hWnd, WindowMessages.WM_KEYUP, new WPARAM(key.getValue()),
                new LPARAM());
        if (!result)
            throw new WinUserException();
    }

    public void sendKey(VirtualKey key, int repeat) {
        boolean result = WindowLib.INSTANCE.PostMessageW(hWnd, WindowMessages.WM_KEYDOWN, new WPARAM(key.getValue()),
                new LPARAM(repeat));
        if (!result)
            throw new WinUserException();
    }
}
