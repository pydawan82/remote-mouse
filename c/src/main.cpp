#include <iostream>
#include <vector>
#include <string>

#include <Windows.h>

#include "mouse.hpp"
#include "window.hpp"
#include "keyboard.hpp"

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
    keyboard::tap('A');

    INPUT inputs[4] = {0};
    inputs[0].type = INPUT_KEYBOARD;
    inputs[0].ki.wVk = VK_LWIN;
    inputs[1].type = INPUT_KEYBOARD;
    inputs[1].ki.wVk = 'D';
    inputs[2].type = INPUT_KEYBOARD;
    inputs[2].ki.wVk = 'D';
    inputs[2].ki.dwFlags = KEYEVENTF_KEYUP;
    inputs[3].type = INPUT_KEYBOARD;
    inputs[3].ki.wVk = VK_LWIN;
    inputs[3].ki.dwFlags = KEYEVENTF_KEYUP;
    SendInput(4, inputs, sizeof(INPUT));

    return 0;
}