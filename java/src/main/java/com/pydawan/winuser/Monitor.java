package com.pydawan.winuser;

import java.util.ArrayList;
import java.util.List;

import com.pydawan.exception.WindowsException;
import com.pydawan.jni.MonitorLib;
import com.sun.jna.Pointer;

import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.RECT;

import com.sun.jna.platform.win32.WinUser.HMONITOR;
import com.sun.jna.platform.win32.WinUser.MONITORINFOEX;

public class Monitor {

    private HMONITOR hMonitor;

    public Monitor(HMONITOR hMonitor) {
        this.hMonitor = hMonitor;
    }

    public static List<Monitor> all() {
        List<Monitor> monitors = new ArrayList<>();
        MonitorLib.INSTANCE.EnumDisplayMonitors(Pointer.NULL, Pointer.NULL,
                (hMonitor, hdcMonitor, lprcMonitor, dwData) -> {
                    monitors.add(new Monitor(hMonitor));
                    return 1;
                }, new LPARAM(0));

        return monitors;
    }

    public MONITORINFOEX getInfo() {
        MONITORINFOEX info = new MONITORINFOEX();
        info.cbSize = info.size();
        boolean result = MonitorLib.INSTANCE.GetMonitorInfoW(hMonitor, info);
        if (!result)
            throw new WindowsException();
        return info;
    }

    public RECT getWorkArea() {
        return getInfo().rcWork;
    }

    public RECT getMonitorArea() {
        return getInfo().rcMonitor;
    }

    public String getName() {
        return new String(getInfo().szDevice);
    }

    @Override
    public String toString() {
        RECT workArea = getWorkArea();
        String name = getName();
        return "Monitor [hMonitor=" + hMonitor + ", name=" + name + ", size=" + (workArea.right - workArea.left)
                + "x" + (workArea.bottom - workArea.top) + "]";
    }

}
