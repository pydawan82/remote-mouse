#pragma once

#include <Windows.h>
#include <vector>

namespace window
{
    BOOL move(HWND hWnd, int dx, int dy);
    BOOL moveAbs(HWND hWnd, int x, int y);
    BOOL maximize(HWND hWnd);
    BOOL maximizeToMonitor(HWND hWnd, MONITORINFOEX *monitor);
    BOOL minimize(HWND hWnd);
    BOOL restore(HWND hWnd);
    BOOL close(HWND hWnd);
    BOOL resize(HWND hWnd, int width, int height);

    std::vector<HWND> getWindows();
    std::vector<MONITORINFOEX> getMonitors();
} // namespace window
