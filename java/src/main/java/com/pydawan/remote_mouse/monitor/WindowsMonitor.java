package com.pydawan.remote_mouse.monitor;

import java.util.ArrayList;
import java.util.List;

import com.pydawan.remote_mouse.exception.LastError;
import com.pydawan.remote_mouse.jni.windows.MonitorLib;
import com.pydawan.remote_mouse.util.Rect;
import com.sun.jna.Pointer;

import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.RECT;

import com.sun.jna.platform.win32.WinUser.HMONITOR;
import com.sun.jna.platform.win32.WinUser.MONITORINFOEX;

public class WindowsMonitor implements Monitor {

    private static short MONITORINFOF_PRIMARY = 1;

    private HMONITOR hMonitor;

    private static Rect toRect(RECT rect) {
        return new Rect(rect.left, rect.top, rect.right, rect.bottom);
    }

    public WindowsMonitor(HMONITOR hMonitor) {
        this.hMonitor = hMonitor;
    }

    public static List<Monitor> all() {
        List<Monitor> monitors = new ArrayList<>();
        MonitorLib.INSTANCE.EnumDisplayMonitors(Pointer.NULL, Pointer.NULL,
                (hMonitor, hdcMonitor, lprcMonitor, dwData) -> {
                    monitors.add(new WindowsMonitor(hMonitor));
                    return 1;
                }, new LPARAM(0));

        return monitors;
    }

    public MONITORINFOEX getInfo() {
        MONITORINFOEX info = new MONITORINFOEX();
        info.cbSize = info.size();
        boolean result = MonitorLib.INSTANCE.GetMonitorInfoW(hMonitor, info);
        if (!result)
            LastError.throwLastError();

        return info;
    }

    public Rect getWorkArea() {
        return toRect(getInfo().rcWork);
    }

    public Rect getMonitorArea() {
        return toRect(getInfo().rcMonitor);
    }

    public String getName() {
        return new String(getInfo().szDevice);
    }

    @Override
    public String toString() {
        MONITORINFOEX info = getInfo();
        RECT monitorArea = info.rcMonitor;
        String name = new String(info.szDevice);
        return "Monitor [hMonitor=" + hMonitor + ", name=" + name + ", size=" + (monitorArea.right - monitorArea.left)
                + "x" + (monitorArea.bottom - monitorArea.top) + "]";
    }

    @Override
    public boolean isPrimary() {
        return getInfo().dwFlags == MONITORINFOF_PRIMARY;
    }

}
