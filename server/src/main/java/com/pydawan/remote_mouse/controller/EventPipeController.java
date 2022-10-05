package com.pydawan.remote_mouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pydawan.remote_mouse.model.PipeInfo;
import com.pydawan.remote_mouse.service.EventPipeService;

@RestController
@RequestMapping("/event-pipe")
public class EventPipeController {

    @Autowired
    EventPipeService eventPipeService;

    @GetMapping("/open")
    public @ResponseBody PipeInfo openPipe() {
        return eventPipeService.open();
    }
}
