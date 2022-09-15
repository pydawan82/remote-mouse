package com.pydawan;

import java.util.List;

import com.pydawan.jni.WindowLib;
import com.pydawan.vk.VirtualKey;
import com.pydawan.vk.VirtualKeyboard;
import com.pydawan.winuser.Monitor;
import com.pydawan.winuser.Shortcut;
import com.pydawan.winuser.Window;
import com.sun.jna.ptr.PointerByReference;

import com.sun.jna.Pointer;

import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.HWND;

import com.sun.jna.platform.win32.WinUser.WNDENUMPROC;
import com.sun.jna.platform.win32.WinUser.WINDOWINFO;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        VirtualKeyboard.pressKeyCombo(VirtualKey.SHIFT, VirtualKey.RWIN, VirtualKey.RIGHT);
        // VirtualKeyboard.pressKeyCombo( VirtualKey.RIGHT);
        // Thread.sleep(1000);
        // VirtualKeyboard.pressKeyCombo(VirtualKey.LEFT);
    }
}
