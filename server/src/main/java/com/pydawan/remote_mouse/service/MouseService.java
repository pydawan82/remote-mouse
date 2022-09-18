package com.pydawan.remote_mouse.service;

import org.springframework.stereotype.Service;

import com.pydawan.remote_mouse.mouse.Mouse;

@Service
public class MouseService {

    private final Mouse mouse;

    public MouseService() {
        mouse = Mouse.getInstance();
    }

    public void move(int dx, int dy) {
        mouse.move(dx, dy);
    }

    public void setPosition(int x, int y) {
        mouse.setPos(x, y);
    }
}
