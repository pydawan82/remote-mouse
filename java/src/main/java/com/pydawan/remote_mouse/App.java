package com.pydawan.remote_mouse;

import com.pydawan.remote_mouse.mouse.Mouse;
import com.pydawan.remote_mouse.mouse.MouseButton;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        Mouse.press(MouseButton.LEFT);
    }
}
