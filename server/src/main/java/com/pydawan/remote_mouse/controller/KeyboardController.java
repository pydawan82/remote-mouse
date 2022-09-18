package com.pydawan.remote_mouse.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pydawan.remote_mouse.model.KeyboardInput;

@RestController
@RequestMapping("/keyboard")
public class KeyboardController {

    @PostMapping("/press")
    public String press(@RequestBody KeyboardInput input) {
        return "Keyboard pressed";
    }

    @PostMapping("/release")
    public String release(@RequestBody KeyboardInput input) {
        return "Keyboard released";
    }

    @PostMapping("/type")
    public String type(@RequestBody KeyboardInput input) {
        return "Keyboard typed";
    }
}
