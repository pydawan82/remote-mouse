package com.pydawan.winuser;

public class WindowStyles {
    public static final int WS_BORDER = 0x00800000;
    public static final int WS_CAPTION = 0x00C00000;
    public static final int WS_CHILD = 0x40000000;
    public static final int WS_CHILDWINDOW = 0x40000000;
    public static final int WS_CLIPCHILDREN = 0x02000000;
    public static final int WS_CLIPSIBLINGS = 0x04000000;
    public static final int WS_DISABLED = 0x08000000;
    public static final int WS_DLGFRAME = 0x00400000;
    public static final int WS_GROUP = 0x00020000;
    public static final int WS_HSCROLL = 0x00100000;
    public static final int WS_ICONIC = 0x20000000;
    public static final int WS_MAXIMIZE = 0x01000000;
    public static final int WS_MAXIMIZEBOX = 0x00010000;
    public static final int WS_MINIMIZE = 0x20000000;
    public static final int WS_MINIMIZEBOX = 0x00020000;
    public static final int WS_OVERLAPPED = 0x00000000;
    public static final int WS_POPUP = 0x80000000;
    public static final int WS_SIZEBOX = 0x00040000;
    public static final int WS_SYSMENU = 0x00080000;
    public static final int WS_TABSTOP = 0x00010000;
    public static final int WS_THICKFRAME = 0x00040000;
    public static final int WS_TILED = 0x00000000;
    public static final int WS_VISIBLE = 0x10000000;
    public static final int WS_VSCROLL = 0x00200000;

    public static final int WS_OVERLAPPEDWINDOW = WS_OVERLAPPED | WS_CAPTION | WS_SYSMENU | WS_THICKFRAME
            | WS_MINIMIZEBOX | WS_MAXIMIZEBOX;
    public static final int WS_POPUPWINDOW = WS_POPUP | WS_BORDER | WS_SYSMENU;
    public static final int WS_TILEDWINDOW = WS_OVERLAPPED | WS_CAPTION | WS_SYSMENU | WS_THICKFRAME | WS_MINIMIZEBOX
            | WS_MAXIMIZEBOX;
}
