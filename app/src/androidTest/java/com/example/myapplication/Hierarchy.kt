package com.example.myapplication

import com.example.myapplication.step.UiNode

/**
 * Nested classes define the view hierarchy. We keep useful information associated with each:
 * 1. The accessibilityId or XPath to find the element by.
 * 2. The error String to use if the view could not be located.
 * 3. The description of what to say if we don't find it.
 */
class Hierarchy {
    class LoginScreen {
        companion object {
            val USERNAME_TEXT_FIELD = UiNode(
                "com.example.company/login_screen_username",
                "Failed to login: Unable to find login text field",
                "Login as a user."
            )
        }
    }
}
