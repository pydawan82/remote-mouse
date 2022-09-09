#include <mouse.hpp>

#include <Windows.h>

static int buttonToUpEvent(mouse::Button button)
{
    switch (button)
    {
    case mouse::Left:
        return MOUSEEVENTF_LEFTUP;
    case mouse::Right:
        return MOUSEEVENTF_RIGHTUP;
    case mouse::Middle:
        return MOUSEEVENTF_MIDDLEUP;
    default:
        return 0;
    }
}

static int buttonToDownEvent(mouse::Button button)
{
    switch (button)
    {
    case mouse::Left:
        return MOUSEEVENTF_LEFTDOWN;
    case mouse::Right:
        return MOUSEEVENTF_RIGHTDOWN;
    case mouse::Middle:
        return MOUSEEVENTF_MIDDLEDOWN;
    default:
        return 0;
    }
}

BOOL mouse::move(int dx, int dy)
{
    POINT p;
    return GetCursorPos(&p) && SetCursorPos(p.x + dx, p.y + dy);
}

BOOL mouse::moveAbs(int x, int y)
{
    return SetCursorPos(x, y);
}

BOOL mouse::click(Button button)
{
    return press(button) && release(button);
}

BOOL mouse::press(Button button)
{
    INPUT input = {0};
    input.type = INPUT_MOUSE;
    input.mi.dwFlags = buttonToDownEvent(button);
    SendInput(1, &input, sizeof(INPUT));

    return TRUE;
}

BOOL mouse::release(Button button)
{
    INPUT input = {0};
    input.type = INPUT_MOUSE;
    input.mi.dwFlags = buttonToUpEvent(button);
    SendInput(1, &input, sizeof(INPUT));

    return TRUE;
}