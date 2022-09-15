package com.pydawan.vk;

import com.sun.jna.Pointer;

import com.sun.jna.platform.win32.WinDef.WORD;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.BaseTSD.ULONG_PTR;

import com.sun.jna.platform.win32.WinUser.INPUT;
import com.sun.jna.platform.win32.WinUser.KEYBDINPUT;

import lombok.NonNull;

import java.util.stream.Stream;

import com.pydawan.exception.WindowsException;
import com.pydawan.jni.InputLib;

public class VirtualKeyboard {

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

    public static void sendKeyDown(VirtualKey key) {
        INPUT input = new INPUT();
        setDownKeyInput(input, key);

        int result = InputLib.INSTANCE.SendInput(1, (INPUT[]) input.toArray(1),
                input.size());

        if (result != 1)
            throw new WindowsException();
    }

    public static void sendKeyUp(VirtualKey key) {
        INPUT input = new INPUT();
        setUpKeyInput(input, key);

        int result = InputLib.INSTANCE.SendInput(1, (INPUT[]) input.toArray(1), input.size());
        if (result != 1)
            throw new WindowsException();
    }

    public static void pressKeys(VirtualKey... keys) {
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

    public static void pressKeyCombo(VirtualKey... keys) {
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
