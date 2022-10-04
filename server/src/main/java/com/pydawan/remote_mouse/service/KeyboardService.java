package com.pydawan.remote_mouse.service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.pydawan.remote_mouse.model.KeyboardInput;
import com.pydawan.remote_mouse.vk.VirtualKey;
import com.pydawan.remote_mouse.vk.VirtualKeyboard;

@Service
public class KeyboardService {

    private final VirtualKeyboard keyboard = VirtualKeyboard.getInstance();

    private final Map<Short, VirtualKey> keyMap;

    public KeyboardService() {
        keyMap = new HashMap<>(VirtualKey.values().length);
        Stream.of(VirtualKey.values())
                .forEach(key -> keyMap.putIfAbsent(key.getValue(), key));
    }

    public void press(KeyboardInput input) {
        VirtualKey vk = keyMap.get(input.getKeyCode());

        if (vk == null)
            throw new IllegalArgumentException("Invalid key");

        keyboard.sendKeyDown(vk);
    }

    public void release(KeyboardInput input) {
        VirtualKey vk = keyMap.get(input.getKeyCode());

        if (vk == null)
            throw new IllegalArgumentException("Invalid key");

        keyboard.sendKeyUp(vk);
    }

    public void type(KeyboardInput input) {
        VirtualKey vk = keyMap.get(input.getKeyCode());

        if (vk == null)
            throw new IllegalArgumentException("Invalid key");

        keyboard.pressKeys(vk);
    }
}
