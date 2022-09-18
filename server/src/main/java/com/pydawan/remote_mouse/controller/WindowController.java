package com.pydawan.remote_mouse.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pydawan.remote_mouse.util.Rect;
import com.pydawan.remote_mouse.window.Window;

@RestController()
@RequestMapping("/window")
public class WindowController {

    private String windowToTableRow(Window window) {
        Rect rect = new Rect(0, 0, 0, 0);
        return String.format("<tr><td>%s</td><td>%dx%d</td></tr>", window.getTitle(), rect.width(), rect.height());
    }

    @GetMapping("/list")
    public String list() {
        List<? extends Window> windows = Window.all();

        return String.format(
                "<table>%s</table>",
                windows.stream()
                        .map(this::windowToTableRow)
                        .collect(Collectors.joining()));
    }

}
