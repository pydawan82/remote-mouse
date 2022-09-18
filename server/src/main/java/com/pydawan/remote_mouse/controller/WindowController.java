package com.pydawan.remote_mouse.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pydawan.remote_mouse.winuser.Window;
import com.sun.jna.platform.win32.WinDef.RECT;

@RestController()
@RequestMapping("/window")
public class WindowController {

    private String windowToTableRow(Window window) {
        RECT rect = window.getRect();
        int width = rect.right - rect.left;
        int height = rect.bottom - rect.top;
        return String.format("<tr><td>%s</td><td>%dx%d</td></tr>", window.getTitle(), width, height);
    }

    @GetMapping("/list")
    public String list() {
        List<Window> windows = Window.collect(Window::isMainWindow);

        // Return an HTML table containing the name and size of each window
        return String.format(
                "<table>%s</table>",
                windows.stream()
                        .map(this::windowToTableRow)
                        .reduce("", String::concat));
    }

}
