package com.pydawan.remote_mouse.model;

import com.pydawan.remote_mouse.winuser.Window;
import com.sun.jna.platform.win32.WinDef.RECT;

public record WindowData(
        String title,
        Rect rect) {

    public static WindowData fromWindow(Window window) {
        RECT rect = window.getRect();
        return new WindowData(
                window.getTitle(),
                Rect.fromRect(rect.left, rect.top, rect.right, rect.bottom));
    }
}
