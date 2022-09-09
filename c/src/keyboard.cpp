#include "keyboard.hpp"

BOOL keyboard::press(WORD key)
{
    INPUT input = {0};
    input.type = INPUT_KEYBOARD;
    input.ki.wVk = key;
    SendInput(1, &input, sizeof(INPUT));

    return TRUE;
}

BOOL keyboard::release(WORD key)
{
    INPUT input = {0};
    input.type = INPUT_KEYBOARD;
    input.ki.wVk = key;
    input.ki.dwFlags = KEYEVENTF_KEYUP;
    SendInput(1, &input, sizeof(INPUT));

    return TRUE;
}

BOOL keyboard::tap(WORD key)
{
    return press(key) && release(key);
}