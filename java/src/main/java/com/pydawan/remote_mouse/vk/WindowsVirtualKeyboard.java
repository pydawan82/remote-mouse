package com.pydawan.remote_mouse.vk;

import com.pydawan.remote_mouse.exception.WindowsException;
import com.pydawan.remote_mouse.jni.windows.InputLib;
import com.sun.jna.platform.win32.WinUser.INPUT;
import com.sun.jna.platform.win32.WinUser.KEYBDINPUT;

import lombok.NonNull;

class WindowsVirtualKeyboard implements VirtualKeyboard {

    private static void setDownKeyInput(@NonNull INPUT input, @NonNull VirtualKey key) {
        input.input.setType(KEYBDINPUT.class);
        input.type.setValue(INPUT.INPUT_KEYBOARD);
        input.input.ki.wVk.setValue(key.getValue());
    }

    private static void setUpKeyInput(INPUT input, VirtualKey key) {
        input.input.setType(KEYBDINPUT.class);
        input.type.setValue(INPUT.INPUT_KEYBOARD);
        input.input.ki.wVk.setValue(key.getValue());
        input.input.ki.dwFlags.setValue(KEYBDINPUT.KEYEVENTF_KEYUP);
    }

    public void sendKeyDown(VirtualKey key) {
        INPUT input = new INPUT();
        setDownKeyInput(input, key);

        int result = InputLib.INSTANCE.SendInput(1, (INPUT[]) input.toArray(1),
                input.size());

        if (result != 1)
            throw new WindowsException();
    }

    public void sendKeyUp(VirtualKey key) {
        INPUT input = new INPUT();
        setUpKeyInput(input, key);

        int result = InputLib.INSTANCE.SendInput(1, (INPUT[]) input.toArray(1), input.size());
        if (result != 1)
            throw new WindowsException();
    }

    public void pressKeys(VirtualKey... keys) {
        if (keys.length == 0)
            return;

        INPUT input = new INPUT();
        INPUT[] inputs = (INPUT[]) input.toArray(keys.length * 2);

        for (int i = 0; i < keys.length; i++) {
            VirtualKey key = keys[i];

            INPUT inputDown = inputs[i * 2];
            setDownKeyInput(inputDown, key);

            INPUT inputUp = inputs[i * 2 + 1];
            setUpKeyInput(inputUp, key);
        }

        int result = InputLib.INSTANCE.SendInput(inputs.length, inputs,
                inputs[0].size());
        if (result != inputs.length)
            throw new WindowsException();
    }

    public void pressKeyCombo(VirtualKey... keys) {
        if (keys.length == 0)
            return;

        INPUT input = new INPUT();
        INPUT[] inputs = (INPUT[]) input.toArray(keys.length * 2);

        for (int i = 0; i < keys.length; i++) {
            VirtualKey key = keys[i];

            INPUT inputDown = inputs[i];
            setDownKeyInput(inputDown, key);

            INPUT inputUp = inputs[keys.length * 2 - (1 + i)];
            setUpKeyInput(inputUp, key);
        }

        int result = InputLib.INSTANCE.SendInput(inputs.length, inputs, inputs[0].size());
        result = inputs.length;
        if (result != inputs.length)
            throw new WindowsException();
    }
}
