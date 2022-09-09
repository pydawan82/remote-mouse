#include <Windows.h>

namespace keyboard
{
    /**
     * @brief Presses the specified keyboard key
     *
     * @param key The key to press
     * @return BOOL Returns TRUE if successful, FALSE otherwise
     */
    BOOL press(WORD key);

    /**
     * @brief Releases the specified keyboard key
     *
     * @param key The key to release
     * @return BOOL Returns TRUE if successful, FALSE otherwise
     */
    BOOL release(WORD key);

    /**
     * @brief Presses and releases the specified keyboard key
     *
     * @param key The key to press and release
     * @return BOOL Returns TRUE if successful, FALSE otherwise
     */
    BOOL tap(WORD key);

} // namespace keyboard