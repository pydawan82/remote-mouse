package com.pydawan.remote_mouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pydawan.remote_mouse.model.Offset;
import com.pydawan.remote_mouse.service.MouseService;

@RestController()
@RequestMapping("/mouse")
public class MouseController {

    @Autowired
    private MouseService mouseService;

    @PostMapping("/move")
    public String move(@RequestBody Offset offset) {
        mouseService.move(offset.getX(), offset.getY());
        return "Mouse moved by " + offset;
    }

}
