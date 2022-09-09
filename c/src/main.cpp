#include <iostream>
#include <vector>
#include <string>

#include <Windows.h>

#include "mouse.hpp"
#include "window.hpp"

void printDebug(std::vector<HWND> windows, std::vector<MONITORINFOEX> monitors)
{

    for (HWND hWnd : windows)
    {
        char title[256];
        GetWindowText(hWnd, title, 256);
        std::cout << title << std::endl;
    }

    for (MONITORINFOEX monitor : monitors)
    {
        std::cout << "Top-left: " << monitor.rcMonitor.left << ", " << monitor.rcMonitor.top << std::endl;
        std::cout << "Bottom-right: " << monitor.rcMonitor.right << ", " << monitor.rcMonitor.bottom << std::endl;
    }
}

int main(int argc, char *args[])
{
    std::vector<HWND> windows = window::getWindows();
    std::vector<MONITORINFOEX> monitors = window::getMonitors();

    mouse::move(20, 20);

    return 0;
}