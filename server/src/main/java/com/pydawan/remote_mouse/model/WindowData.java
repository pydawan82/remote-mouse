package com.pydawan.remote_mouse.model;

import com.pydawan.remote_mouse.util.Rect;
import com.pydawan.remote_mouse.window.Window;

public record WindowData(
        String title,
        RectData rect) {

    public static WindowData fromWindow(Window window) {
        Rect rect = new Rect(0, 0, 0, 0); // window.getRect();
        return new WindowData(window.getTitle(), RectData.fromRect(rect));
    }
}
