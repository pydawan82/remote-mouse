package com.pydawan.remote_mouse.mouse;

import com.sun.jna.Platform;

public interface Mouse {

    public void move(int x, int y);

    public void setPos(int x, int y);

    public void press(MouseButton button);

    public void release(MouseButton button);

    public void click(MouseButton button);

    public void scroll(int dx, int dy);

    public static Mouse getInstance() {
        return switch (Platform.getOSType()) {
            case Platform.WINDOWS -> new WindowsMouse();
            default -> throw new UnsupportedOperationException("Unsupported OS");
        };
    }
}
