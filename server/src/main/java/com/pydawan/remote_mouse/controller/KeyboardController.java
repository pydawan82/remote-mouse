package com.pydawan.remote_mouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.pydawan.remote_mouse.model.KeyboardInput;
import com.pydawan.remote_mouse.service.KeyboardService;

@RestController
@RequestMapping("/keyboard")
public class KeyboardController {

    @Autowired
    private KeyboardService keyboardService;

    @PostMapping("/press")
    public void press(@RequestBody KeyboardInput input) {
        try {
            keyboardService.press(input);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/release")
    public void release(@RequestBody KeyboardInput input) {
        try {
            keyboardService.release(input);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/type")
    public void type(@RequestBody KeyboardInput input) {
        try {
            keyboardService.type(input);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
