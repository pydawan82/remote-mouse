package com.pydawan.remote_mouse;

import com.pydawan.remote_mouse.monitor.Monitor;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        System.out.println(Monitor.getPrimary().getMonitorArea());
    }
}
