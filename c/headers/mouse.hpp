#pragma once

#include <Windows.h>

namespace mouse
{
    typedef enum
    {
        Left,
        Right,
        Middle
    } Button;

    /**
     * @brief Moves the mouse cursor by the specified amount
     *
     * @param dx The amount to move the mouse cursor on the x-axis
     * @param dy The amount to move the mouse cursor on the y-axis
     * @return BOOL Returns TRUE if successful, FALSE otherwise
     */
    BOOL move(int dx, int dy);
    /**
     * @brief Moves the mouse cursor to the specified position
     *
     * @param x The x-coordinate to move the mouse cursor to
     * @param y The y-coordinate to move the mouse cursor to
     * @return BOOL Returns TRUE if successful, FALSE otherwise
     */
    BOOL moveAbs(int x, int y);
    /**
     * @brief Clicks the specified mouse button
     *
     * @param button The mouse button to click
     * @return BOOL Returns TRUE if successful, FALSE otherwise
     */
    BOOL click(Button button);

    /**
     * @brief Presses the specified mouse button
     *
     * @param button The mouse button to press
     * @return BOOL Returns TRUE if successful, FALSE otherwise
     */
    BOOL press(Button button);

    /**
     * @brief Releases the specified mouse button
     *
     * @param button The mouse button to release
     * @return BOOL Returns TRUE if successful, FALSE otherwise
     */
    BOOL release(Button button);

} // namespace mouse
