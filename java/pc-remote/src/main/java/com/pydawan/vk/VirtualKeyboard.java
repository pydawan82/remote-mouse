package com.pydawan.vk;

import com.sun.jna.Pointer;

import com.sun.jna.platform.win32.WinDef.WORD;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.BaseTSD.ULONG_PTR;

import com.sun.jna.platform.win32.WinUser.INPUT;
import com.sun.jna.platform.win32.WinUser.KEYBDINPUT;

import java.util.stream.Stream;

import com.pydawan.exception.WindowsException;
import com.pydawan.jni.InputLib;

public class VirtualKeyboard {

    public static void sendKeyDown(VirtualKey key) {
        INPUT input = new INPUT();
        input.input.setType(KEYBDINPUT.class);
        input.type.setValue(INPUT.INPUT_KEYBOARD);
        input.input.ki.wVk.setValue(key.getValue());

        int result = InputLib.INSTANCE.SendInput(1, (INPUT[]) input.toArray(1),
                input.size());

        if (result != 1)
            throw new WindowsException();
    }

    public static void sendKeyUp(VirtualKey key) {
        INPUT input = new INPUT();
        input.input.setType(KEYBDINPUT.class);
        input.type.setValue(INPUT.INPUT_KEYBOARD);
        input.input.ki.wVk.setValue(key.getValue());
        input.input.ki.dwFlags.setValue(2);

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
            input.input.setType(KEYBDINPUT.class);
            inputDown.type.setValue(INPUT.INPUT_KEYBOARD);
            inputDown.input.ki.wVk.setValue(key.getValue());

            INPUT inputUp = inputs[i * 2 + 1];
            input.input.setType(KEYBDINPUT.class);
            inputUp.type.setValue(INPUT.INPUT_KEYBOARD);
            inputUp.input.ki.wVk.setValue(key.getValue());
            inputUp.input.ki.dwFlags.setValue(2);
        }

        Stream.of(inputs).forEach(System.out::println);

        int result = InputLib.INSTANCE.SendInput(inputs.length, inputs, inputs[0].size());
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
            input.input.setType(KEYBDINPUT.class);
            inputDown.type.setValue(INPUT.INPUT_KEYBOARD);
            inputDown.input.ki.wVk.setValue(key.getValue());

            INPUT inputUp = inputs[keys.length * 2 - (1 + i)];
            input.input.setType(KEYBDINPUT.class);
            inputUp.type.setValue(INPUT.INPUT_KEYBOARD);
            inputUp.input.ki.wVk.setValue(key.getValue());
            inputUp.input.ki.dwFlags.setValue(2);
        }

        Stream.of(inputs).forEach(System.out::println);

        int result = InputLib.INSTANCE.SendInput(inputs.length, inputs, inputs[0].size());
        result = inputs.length;
        if (result != inputs.length)
            throw new WindowsException();
    }
}
