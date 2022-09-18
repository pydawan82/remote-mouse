package com.pydawan.remote_mouse.window.windows;

import java.util.List;

import com.pydawan.remote_mouse.vk.VirtualKey;

public final class WindowsCombo {
        private WindowsCombo() {
        }

        public static final List<VirtualKey> showDesktop = List.of(VirtualKey.LWIN, VirtualKey.D);
        public static final List<VirtualKey> showTaskView = List.of(VirtualKey.LWIN, VirtualKey.TAB);
        public static final List<VirtualKey> showStartMenu = List.of(VirtualKey.LWIN);
        public static final List<VirtualKey> showNotification = List.of(VirtualKey.LWIN, VirtualKey.A);
        public static final List<VirtualKey> moveToNextDesktop = List.of(VirtualKey.LWIN, VirtualKey.LCONTROL,
                        VirtualKey.RIGHT);
        public static final List<VirtualKey> moveToPreviousDesktop = List.of(VirtualKey.LWIN, VirtualKey.LCONTROL,
                        VirtualKey.LEFT);
}
