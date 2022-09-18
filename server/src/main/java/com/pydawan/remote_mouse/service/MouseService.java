package com.pydawan.remote_mouse.service;

import org.springframework.stereotype.Service;

import com.pydawan.remote_mouse.mouse.Mouse;

@Service
public class MouseService {

    public void move(int dx, int dy) {
        Mouse.move(dx, dy);
    }

    public void setPosition(int x, int y) {
        Mouse.setPos(x, y);
    }
}
