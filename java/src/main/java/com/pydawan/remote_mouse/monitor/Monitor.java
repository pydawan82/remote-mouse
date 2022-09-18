package com.pydawan.remote_mouse.monitor;

import java.util.List;

import com.pydawan.remote_mouse.util.Rect;

import com.sun.jna.Platform;

public interface Monitor {

    /**
     * The greatest area of the monitor that is available for applications to draw
     * 
     * @return a {@link Rect} representing the work area
     */
    public Rect getWorkArea();

    /**
     * The total area of the monitor
     * 
     * @return a {@link Rect} representing the monitor area
     */
    public Rect getMonitorArea();

    /**
     * The name of the monitor
     * 
     * @return the name of the monitor
     */
    public String getName();

    /**
     * Tells whether the monitor is the primary monitor
     * 
     * @return <code>true</code> if the monitor is the primary monitor,
     *         <code>false</code> otherwise
     */
    public boolean isPrimary();

    /**
     * Gets all the monitors
     * 
     * @return a {@link List} of {@link Monitor}
     */
    public static List<Monitor> all() {
        return switch (Platform.getOSType()) {
            case Platform.WINDOWS -> WindowsMonitor.all();
            default -> throw new UnsupportedOperationException("Unsupported OS");
        };
    };

    /**
     * Gets the primary monitor
     */
    public static Monitor getPrimary() {
        return all().stream().filter(Monitor::isPrimary).findFirst().get();
    }
}
