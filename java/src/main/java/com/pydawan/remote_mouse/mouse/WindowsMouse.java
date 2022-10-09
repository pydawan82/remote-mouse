package com.pydawan.remote_mouse.mouse;

import com.sun.jna.platform.win32.WinUser.INPUT;
import com.sun.jna.platform.win32.WinUser.MOUSEINPUT;

import static com.pydawan.remote_mouse.mouse.MouseEventFlag.*;

import com.pydawan.remote_mouse.exception.LastError;
import com.pydawan.remote_mouse.jni.windows.InputLib;

class WindowsMouse implements Mouse {

    private static INPUT setMouseInput(INPUT input) {
        input.input.setType(MOUSEINPUT.class);
        input.type.setValue(INPUT.INPUT_MOUSE);

        return input;
    }

    private static INPUT[] getInputArray(int size) {
        INPUT input = new INPUT();
        return (INPUT[]) input.toArray(size);
    }

    public void move(int x, int y) {
        INPUT input = setMouseInput(new INPUT());
        MOUSEINPUT mi = input.input.mi;

        mi.dx.setValue(x);
        mi.dy.setValue(y);
        mi.dwFlags.setValue(MOVE.getValue());

        int result = InputLib.INSTANCE.SendInput(1, input, input.size());
        if (result != 1)
            LastError.throwLastError();
    }

    public void setPos(int x, int y) {
        INPUT input = setMouseInput(new INPUT());
        MOUSEINPUT mi = input.input.mi;

        mi.dx.setValue(x);
        mi.dy.setValue(y);
        mi.dwFlags.setValue(getFlags(MOVE, ABSOLUTE));

        int result = InputLib.INSTANCE.SendInput(1, input, input.size());
        if (result != 1)
            LastError.throwLastError();
    }

    public void press(MouseButton button) {
        INPUT input = setMouseInput(new INPUT());
        MOUSEINPUT mi = input.input.mi;

        MouseButton.setDownEvent(mi, button);

        int result = InputLib.INSTANCE.SendInput(1, input, input.size());
        if (result != 1)
            LastError.throwLastError();
    }

    public void release(MouseButton button) {
        INPUT input = setMouseInput(new INPUT());
        MOUSEINPUT mi = input.input.mi;

        MouseButton.setUpEvent(mi, button);

        int result = InputLib.INSTANCE.SendInput(1, input, input.size());
        if (result != 1)
            LastError.throwLastError();
    }

    public void click(MouseButton button) {
        press(button);
        release(button);
    }

    public void scroll(int dy) {
        INPUT input = setMouseInput(new INPUT());
        MOUSEINPUT mi = input.input.mi;

        mi.mouseData.setValue(dy);
        mi.dwFlags.setValue(WHEEL.getValue());

        int result = InputLib.INSTANCE.SendInput(1, input, input.size());
        if (result != 1)
            LastError.throwLastError();
    }

    public void hscroll(int dx) {
        INPUT input = setMouseInput(new INPUT());
        MOUSEINPUT mi = input.input.mi;

        mi.mouseData.setValue(dx);
        mi.dwFlags.setValue(HWHEEL.getValue());

        int result = InputLib.INSTANCE.SendInput(1, input, input.size());
        if (result != 1)
            LastError.throwLastError();
    }
}
