package com.pydawan.remote_mouse.winuser;

import com.pydawan.remote_mouse.vk.VirtualKey;
import com.pydawan.remote_mouse.vk.VirtualKeyboard;

public class Shortcut {
    private Shortcut() {
    }

    /**
     * Windows + D
     */
    public static void showDesktop() {
        VirtualKeyboard.pressKeyCombo(VirtualKey.LWIN, VirtualKey.D);
    }

    /**
     * Windows + Ctrl + Right
     */
    public static void moveToNextDesktop() {
        VirtualKeyboard.pressKeyCombo(VirtualKey.LWIN, VirtualKey.LCONTROL, VirtualKey.RIGHT);
    }

    /**
     * Windows + Ctrl + Left
     */
    public static void moveToPreviousDesktop() {
        VirtualKeyboard.pressKeyCombo(VirtualKey.LWIN, VirtualKey.LCONTROL, VirtualKey.LEFT);
    }

    /**
     * Windows + Tab
     */
    public static void showTaskView() {
        VirtualKeyboard.pressKeyCombo(VirtualKey.LWIN, VirtualKey.TAB);
    }

    /**
     * Windows + A
     */
    public static void showActionCenter() {
        VirtualKeyboard.pressKeyCombo(VirtualKey.LWIN, VirtualKey.A);
    }

}
