package com.pydawan.remote_mouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.pydawan.remote_mouse.model.Button;
import com.pydawan.remote_mouse.model.Offset;
import com.pydawan.remote_mouse.service.MouseService;

@RestController()
@RequestMapping("/mouse")
public class MouseController {

    @Autowired
    private MouseService mouseService;

    @GetMapping("/move")
    public void move() {
        System.out.println("move");
    }

    @PostMapping("/move")
    public void move(@RequestBody Offset offset) {
        mouseService.move(offset.getX(), offset.getY());
    }

    @PostMapping("/click")
    public void click(@RequestBody Button button) {
        try {
            mouseService.click(button);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/scroll")
    public void scroll(@RequestBody Offset offset) {
        mouseService.scroll(offset);
    }

}
