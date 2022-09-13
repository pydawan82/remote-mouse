package com.pydawan;

import java.util.List;

import com.pydawan.jni.WindowLib;
import com.pydawan.vk.VirtualKey;
import com.pydawan.vk.VirtualKeyboard;
import com.pydawan.winuser.Monitor;
import com.pydawan.winuser.Window;
import com.sun.jna.ptr.PointerByReference;

import com.sun.jna.Pointer;

import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.HWND;

import com.sun.jna.platform.win32.WinUser.WNDENUMPROC;
import com.sun.jna.platform.win32.WinUser.WINDOWINFO;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws Exception {
        System.out.println(Window.foreground());
        List<Window> windows = Window.collect(Window::isMainWindow);
        List<Monitor> monitors = Monitor.all();
        windows.get(1).maximizeTo(monitors.get(2));
        VirtualKeyboard.pressKeys(VirtualKey.F, VirtualKey.SPACE);
    }
}
