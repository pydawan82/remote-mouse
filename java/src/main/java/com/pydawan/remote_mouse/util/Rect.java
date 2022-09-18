package com.pydawan.remote_mouse.util;

public record Rect(
        int left,
        int top,
        int right,
        int bottom) {
    public int width() {
        return right - left;
    }

    public int height() {
        return bottom - top;
    }
}