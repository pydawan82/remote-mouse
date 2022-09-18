package com.pydawan.remote_mouse.model;

import com.pydawan.remote_mouse.util.Rect;

public record RectData(
        int left,
        int top,
        int right,
        int bottom) {

    public static RectData fromRect(Rect rect) {
        return new RectData(rect.left(), rect.top(), rect.right(), rect.bottom());
    }

    public int width() {
        return right - left;
    }

    public int height() {
        return bottom - top;
    }
}
