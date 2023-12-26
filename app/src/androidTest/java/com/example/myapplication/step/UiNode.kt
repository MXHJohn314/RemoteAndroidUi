package com.example.myapplication.step

import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2

class UiNode(val accessibilityId: String, val failureToFindString: String, val description: String) {
    private var obj: UiObject2? = null
    fun findUiObjectWithDevice(uiDevice: UiDevice): UiObject2? {
        obj = uiDevice.findObject(By.res(accessibilityId))
        return obj
    }
}
