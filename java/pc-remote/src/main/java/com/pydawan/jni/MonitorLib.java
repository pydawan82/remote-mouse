package com.pydawan.jni;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import com.sun.jna.platform.win32.WinUser.MONITORENUMPROC;
import com.sun.jna.platform.win32.WinDef.LPARAM;

import com.sun.jna.platform.win32.WinUser.HMONITOR;
import com.sun.jna.platform.win32.WinUser.MONITORENUMPROC;
import com.sun.jna.platform.win32.WinUser.HMONITOR;
import com.sun.jna.platform.win32.WinUser.MONITORINFOEX;

public interface MonitorLib extends Library {
    public static MonitorLib INSTANCE = Native.load("user32", MonitorLib.class);

    public boolean GetMonitorInfoW(HMONITOR hMonitor, MONITORINFOEX lpmi);

    public boolean EnumDisplayMonitors(Pointer hdc, Pointer lprcClip, MONITORENUMPROC lpfnEnum, LPARAM dwData);
}
