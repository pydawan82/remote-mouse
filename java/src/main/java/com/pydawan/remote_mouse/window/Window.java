package com.pydawan.remote_mouse.window;

import java.util.List;
import java.util.function.Predicate;

import com.pydawan.remote_mouse.monitor.Monitor;
import com.pydawan.remote_mouse.vk.VirtualKey;
import com.pydawan.remote_mouse.window.windows.WindowsWindow;
import com.sun.jna.Platform;

public interface Window {

    public String getTitle();

    /**
     * Set the window to the foreground.
     */
    public void setForeground();

    /**
     * Maximizes the window.
     */
    public void maximize();

    /**
     * Minimizes the window.
     */
    public void minimize();

    /**
     * Restores the window to its previous state.
     */
    public void restore();

    /**
     * Sends a message to close the window.
     */
    public void close();

    /**
     * Sends a key press to the window.
     * 
     * @param key The key to press.
     */
    public void sendKeyDown(VirtualKey key);

    /**
     * Sends a key up message to the window.
     * Unknown behavior for now.
     * 
     * @param key The key to send.
     */
    public void sendKeyUp(VirtualKey key);

    public void sendKey(VirtualKey key, int repeat);

    public void move(int x, int y);

    public void resize(int width, int height);

    public void moveAndResize(int x, int y, int width, int height);

    public void maximizeTo(Monitor m);

    public static List<? extends Window> all() {
        return switch (Platform.getOSType()) {
            case Platform.WINDOWS -> WindowsWindow.all().stream().map(w -> (Window) w).toList();
            default -> throw new UnsupportedOperationException("Unsupported OS");
        };
    }

    public static List<? extends Window> collect(Predicate<Window> predicate) {
        return switch (Platform.getOSType()) {
            case Platform.WINDOWS -> WindowsWindow.collect(predicate);
            default -> throw new UnsupportedOperationException("Unsupported OS");
        };
    }

    public static Window foreground() {
        return switch (Platform.getOSType()) {
            case Platform.WINDOWS -> WindowsWindow.foreground();
            default -> throw new UnsupportedOperationException("Unsupported OS");
        };
    }
}
