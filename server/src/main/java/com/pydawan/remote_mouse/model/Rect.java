package com.pydawan.remote_mouse.model;

public record Rect(
        int left,
        int top,
        int right,
        int bottom) {

    public static Rect fromRect(int left, int top, int right, int bottom) {
        return new Rect(left, top, right, bottom);
    }

    public int getWidth() {
        return right - left;
    }

    public int getHeight() {
        return bottom - top;
    }
}
