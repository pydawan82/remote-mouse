package com.pydawan.remote_mouse.mouse;

import lombok.Getter;

public enum MouseEventFlag {
    MOVE((short) 0x0001),
    LEFTDOWN((short) 0x0002),
    LEFTUP((short) 0x0004),
    RIGHTDOWN((short) 0x0008),
    RIGHTUP((short) 0x0010),
    MIDDLEDOWN((short) 0x0020),
    MIDDLEUP((short) 0x0040),
    XDOWN((short) 0x0080),
    XUP((short) 0x0100),
    WHEEL((short) 0x0800),
    HWHEEL((short) 0x01000),
    MOVE_NOCOALESCE((short) 0x2000),
    VIRTUALDESK((short) 0x4000),
    ABSOLUTE((short) 0x8000);

    @Getter
    private short value;

    MouseEventFlag(short value) {
        this.value = value;
    }

    public static short getFlags(MouseEventFlag... flags) {
        short result = 0;

        for (MouseEventFlag flag : flags) {
            result |= flag.getValue();
        }

        return result;
    }
}