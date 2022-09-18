package com.pydawan.remote_mouse.vk;

import com.sun.jna.Platform;

public interface VirtualKeyboard {
    public void sendKeyDown(VirtualKey key);

    public void sendKeyUp(VirtualKey key);

    public void pressKeys(VirtualKey... keys);

    public void pressKeyCombo(VirtualKey... keys);

    public static VirtualKeyboard getInstance() {
        return switch (Platform.getOSType()) {
            case Platform.WINDOWS -> new WindowsVirtualKeyboard();
            default -> throw new UnsupportedOperationException("Unsupported OS");
        };
    }
}
