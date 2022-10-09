package com.pydawan.remote_mouse;

import com.pydawan.remote_mouse.mouse.Mouse;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        Mouse mouse = Mouse.getInstance();
        mouse.scroll(50);
    }
}
