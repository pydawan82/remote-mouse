package com.pydawan.remote_mouse.window.windows;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import lombok.Getter;

import com.pydawan.remote_mouse.exception.WindowsException;
import com.pydawan.remote_mouse.jni.windows.WindowLib;
import com.pydawan.remote_mouse.monitor.Monitor;
import com.pydawan.remote_mouse.util.Rect;
import com.pydawan.remote_mouse.vk.VirtualKey;
import com.pydawan.remote_mouse.window.Window;
import com.sun.jna.Pointer;

import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.RECT;

import com.sun.jna.platform.win32.WinUser.WNDENUMPROC;
import com.sun.jna.platform.win32.WinUser.WINDOWINFO;

public class WindowsWindow implements Window {

    @Getter
    private HWND hWnd;

    private static Rect toRect(RECT rect) {
        return new Rect(rect.left, rect.top, rect.right, rect.bottom);
    }

    protected WindowsWindow(HWND hWnd) {
        this.hWnd = hWnd;
    }

    public static WindowsWindow foreground() {
        return new WindowsWindow(WindowLib.INSTANCE.GetForegroundWindow());
    }

    /**
     * Enumerates all windows until the predicate returns false
     * or all windows have been enumerated.
     * 
     * @param predicate The predicate to test each window with.
     * @
     */
    public static void enumerate(Predicate<? super WindowsWindow> predicate) {
        WNDENUMPROC fn = new WNDENUMPROC() {
            public boolean callback(HWND hWnd, Pointer arg1) {
                return predicate.test(new WindowsWindow(hWnd));
            }
        };

        boolean result = WindowLib.INSTANCE.EnumWindows(fn, new LPARAM());
        if (!result)
            throw new WindowsException();
    }

    public static List<WindowsWindow> all() {
        List<WindowsWindow> windows = new ArrayList<>();
        enumerate(windows::add);
        return windows;
    }

    /**
     * Collects all windows that match the predicate.
     * 
     * @param predicate The predicate to test each window with.
     * @return A list of all windows that match the predicate.
     */
    public static List<WindowsWindow> collect(Predicate<? super WindowsWindow> predicate) {
        List<WindowsWindow> windows = new ArrayList<>();

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
            throw new WindowsException();

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
            throw new WindowsException();

        return info;
    }

    /**
     * Gets the rectangle of the window.
     */
    public Rect getRect() {
        return toRect(getInfo().rcWindow);
    }

    @Override
    public String toString() {
        Rect rect = getRect();

        int width = rect.width();
        int height = rect.height();

        return String.format("Window[title=%s, pos=%sx%s, dim=%sx%s]", getTitle(), rect.left(), rect.top(), width,
                height);
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
            throw new WindowsException();
    }

    /**
     * Maximizes the window.
     */
    public void maximize() {
        boolean result = WindowLib.INSTANCE.ShowWindow(hWnd, ShowWindow.SW_MAXIMIZE);
        if (!result)
            throw new WindowsException();
    }

    /**
     * Minimizes the window.
     */
    public void minimize() {
        boolean result = WindowLib.INSTANCE.ShowWindow(hWnd, ShowWindow.SW_MINIMIZE);
        if (!result)
            throw new WindowsException();
    }

    /**
     * Restores the window to its previous state.
     */
    public void restore() {
        boolean result = WindowLib.INSTANCE.ShowWindow(hWnd, ShowWindow.SW_RESTORE);
        if (!result)
            throw new WindowsException();
    }

    /**
     * Sends a message to close the window.
     */
    public void close() {
        boolean result = WindowLib.INSTANCE.PostMessageW(hWnd, WindowMessages.WM_CLOSE, new WPARAM(), new LPARAM());
        if (!result)
            throw new WindowsException();
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
            throw new WindowsException();
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
            throw new WindowsException();
    }

    public void sendKey(VirtualKey key, int repeat) {
        boolean result = WindowLib.INSTANCE.PostMessageW(hWnd, WindowMessages.WM_KEYDOWN, new WPARAM(key.getValue()),
                new LPARAM(repeat));
        if (!result)
            throw new WindowsException();
    }

    public void move(int x, int y) {
        boolean result = WindowLib.INSTANCE.SetWindowPos(hWnd, hWnd, x, y, 0, 0, 0x5);
        if (!result)
            throw new WindowsException();
    }

    public void resize(int width, int height) {
        boolean result = WindowLib.INSTANCE.SetWindowPos(hWnd, hWnd, 0, 0, width, height, 0x6);
        if (!result)
            throw new WindowsException();
    }

    public void moveAndResize(int x, int y, int width, int height) {
        boolean result = WindowLib.INSTANCE.SetWindowPos(hWnd, hWnd, x, y, width, height, 0x4);
        if (!result)
            throw new WindowsException();
    }

    public void maximizeTo(Monitor m) {
        setForeground();
        Rect rect = m.getWorkArea();
        move(rect.left(), rect.top());
        maximize();
    }
}
