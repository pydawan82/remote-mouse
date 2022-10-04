package com.pydawan.remote_mouse.service;

import org.springframework.stereotype.Service;

import com.pydawan.remote_mouse.model.Button;
import com.pydawan.remote_mouse.model.Offset;
import com.pydawan.remote_mouse.mouse.Mouse;
import com.pydawan.remote_mouse.mouse.MouseButton;

@Service
public class MouseService {

    private final Mouse mouse = Mouse.getInstance();
    private final MouseButton[] buttons = MouseButton.values();

    public void move(int dx, int dy) {
        mouse.move(dx, dy);
    }

    public void setPosition(int x, int y) {
        mouse.setPos(x, y);
    }

    public void click(Button button) throws IllegalArgumentException {
        int buttonCode = button.getButton();

        if (buttonCode < 0 || buttonCode >= buttons.length)
            throw new IllegalArgumentException("Invalid button");

        MouseButton mbutton = buttons[buttonCode];
        mouse.click(mbutton);
    }

    public void scroll(Offset offset) {
        mouse.scroll(offset.getX(), offset.getY());
    }
}
