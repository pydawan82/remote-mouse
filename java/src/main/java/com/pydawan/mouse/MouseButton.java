package com.pydawan.mouse;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.sun.jna.platform.win32.WinUser.MOUSEINPUT;

import static com.pydawan.mouse.MouseEventFlag.*;

@Getter
@AllArgsConstructor
public enum MouseButton {
    LEFT(LEFTUP, LEFTDOWN, (short) 0x0000),
    RIGHT(RIGHTUP, RIGHTDOWN, (short) 0x0000),
    MIDDLE(MIDDLEUP, MIDDLEDOWN, (short) 0x0000),
    X1(XUP, XDOWN, (short) 0x0001),
    X2(XUP, XDOWN, (short) 0x0002);

    private final MouseEventFlag upFlag;
    private final MouseEventFlag downFlag;
    private final short value;

    public static MOUSEINPUT setDownEvent(MOUSEINPUT mi, MouseButton button) {
        mi.dwFlags.setValue(getFlags(button.getDownFlag()));
        mi.mouseData.setValue(button.getValue());

        return mi;
    }

    public static MOUSEINPUT setUpEvent(MOUSEINPUT mi, MouseButton button) {
        mi.dwFlags.setValue(getFlags(button.getUpFlag()));
        mi.mouseData.setValue(button.getValue());

        return mi;
    }
}