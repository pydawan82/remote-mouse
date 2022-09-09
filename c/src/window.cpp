#include <window.hpp>

#include <iostream>

static int test()
{
    EnumDisplayMonitors(
        NULL, NULL, [](HMONITOR hMonitor, HDC hdcMonitor, LPRECT lprcMonitor, LPARAM dwData) -> BOOL
        {
        MONITORINFOEX monitorInfo;
        monitorInfo.cbSize = sizeof(MONITORINFOEX);
        GetMonitorInfo(hMonitor, &monitorInfo);
        std::cout << monitorInfo.szDevice << " " << monitorInfo.rcMonitor.right - monitorInfo.rcMonitor.left << "x" << monitorInfo.rcMonitor.bottom - monitorInfo.rcMonitor.top << std::endl;
        return TRUE; },
        NULL);
}

BOOL window::move(HWND hWnd, int dx, int dy)
{
    RECT rect;
    return GetWindowRect(hWnd, &rect) && SetWindowPos(hWnd, NULL, rect.left + dx, rect.top + dy, 0, 0, SWP_NOSIZE);
}

BOOL window::moveAbs(HWND hWnd, int x, int y)
{
    return SetWindowPos(hWnd, NULL, x, y, 0, 0, SWP_NOSIZE);
}

BOOL window::maximize(HWND hWnd)
{
    return ShowWindow(hWnd, SW_MAXIMIZE);
}

BOOL window::maximizeToMonitor(HWND hWnd, MONITORINFOEX *monitor)
{
    int X = monitor->rcMonitor.left;
    int Y = monitor->rcMonitor.top;
    return SetWindowPos(hWnd, 0, X, Y, 0, 0, SWP_NOSIZE) && SetForegroundWindow(hWnd) && maximize(hWnd);
}

BOOL window::minimize(HWND hWnd)
{
    return ShowWindow(hWnd, SW_MINIMIZE);
}

BOOL window::restore(HWND hWnd)
{
    return ShowWindow(hWnd, SW_RESTORE);
}

BOOL window::close(HWND hWnd)
{
    return PostMessage(hWnd, WM_CLOSE, 0, 0);
}

std::vector<HWND> window::getWindows()
{
    std::vector<HWND> windows;
    EnumWindows(
        [](HWND hWnd, LPARAM lParam) -> BOOL
        {
            std::vector<HWND> *windows = (std::vector<HWND> *)lParam;
            WINDOWINFO windowInfo;
            windowInfo.cbSize = sizeof(WINDOWINFO);
            GetWindowInfo(hWnd, &windowInfo);
            if (windowInfo.dwStyle & WS_VISIBLE && windowInfo.dwStyle & WS_OVERLAPPEDWINDOW)
                windows->push_back(hWnd);
            return TRUE;
        },
        (LPARAM)&windows);

    return windows;
}

std::vector<MONITORINFOEX> window::getMonitors()
{
    std::vector<MONITORINFOEX> monitors;
    EnumDisplayMonitors(
        NULL, NULL, [](HMONITOR hMonitor, HDC hdcMonitor, LPRECT lprcMonitor, LPARAM dwData) -> BOOL
        {
            std::vector<MONITORINFOEX> *monitors = (std::vector<MONITORINFOEX> *)dwData;
            MONITORINFOEX monitorInfo;
            monitorInfo.cbSize = sizeof(MONITORINFOEX);
            GetMonitorInfo(hMonitor, &monitorInfo);
            monitors->push_back(monitorInfo);
            return TRUE; },
        (LPARAM)&monitors);

    return monitors;
}
