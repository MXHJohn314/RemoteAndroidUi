package com.example.myapplication

import android.graphics.Point
import android.graphics.Rect
import android.service.notification.Condition
import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.EventCondition
import androidx.test.uiautomator.SearchCondition
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.UiObject2Condition
import com.example.myapplication.RemoteUiObject

class ArTestIoTAndroidObject(
    val obj: UiObject,
    val ob2: UiObject2,
): RemoteUiObject("") {
    /**
     */
    fun clear() {}

    /**
     */
    fun click() {}

    /**
     */
    fun click(duration: Long) {}

    /**
     */
    fun click(point: Point) {}

    /**
     */
    fun click(point: Point, duration: Long) {}

    /**
     */
    fun <U> clickAndWait(condition: EventCondition<U>, timeout: Long) {}

    /**
     */
    fun <U> clickAndWait(point: Point, condition: EventCondition<U>, timeout: Long) {}

    /**
     */
    fun drag(dest: Point) {}

    /**
     */
    fun drag(dest: Point, speed: Int) {}

    /**
     */
    fun equals(o: Any)/*: Boolean*/ {}

    /**
     */
    fun findObject(selector: BySelector)/*UiObject2*/{}

    /**
     */
    fun findObjects(selector: BySelector)/*List<UiObject2>*/{}

    /**
     */
    fun fling(direction: Direction)/*Boolean*/{}

    /**
     */
    fun fling(direction: Direction, speed: Int)/*Boolean*/{}

    /**
     */
    fun getApplicationPackage()/*String*/{}

    /**
     */
    fun getChildCount()/*Int*/{}

    /**
     */
    fun getChildren()/*List<UiObject2>*/{}

    /**
     */
    fun getClassName()/*String*/{}

    /**
     */
    fun getContentDescription()/*String*/{}

    /**
     */
    fun getDisplayId()/*Int*/{}

    /**
     */
    fun getDrawingOrder()/*Int*/{}

    /**
     */
    fun getHint()/*String?*/{}

    /**
     */
    fun getParent()/*UiObject2*/{}

    /**
     */
    fun getResourceName()/*String*/{}

    /**
     */
    fun getText()/*String*/{}

    /**
     */
    fun getVisibleBounds()/*Rect*/{}

    /**
     */
    fun getVisibleCenter()/*Point*/{}

    /**
     */
    fun hasObject(selector: BySelector)/*Boolean*/{}

    /**
     */
    override fun hashCode()/*Int*/=0

    /**
     */
    fun isCheckable()/*Boolean*/{}

    /**
     */
    fun isChecked()/*Boolean*/{}

    /**
     */
    fun isClickable()/*Boolean*/{}

    /**
     */
    fun isEnabled()/*Boolean*/{}

    /**
     */
    fun isFocusable()/*Boolean*/{}

    /**
     */
    fun isFocused()/*Boolean*/{}

    /**
     */
    fun isLongClickable()/*Boolean*/{}

    /**
     */
    fun isScrollable()/*Boolean*/{}

    /**
     */
    fun isSelected()/*Boolean*/{}

    /**
     */
    fun longClick() {}

    /**
     */
    fun pinchClose(percent: Float) {}

    /**
     */
    fun pinchClose(percent: Float, speed: Int) {}

    /**
     */
    fun pinchOpen(percent: Float) {}

    /**
     */
    fun pinchOpen(percent: Float, speed: Int) {}

    /**
     */
    fun recycle() {}

    /**
     */
    fun scroll(direction: Direction, percent: Float)/*Boolean*/{}

    /**
     */
    fun scroll(direction: Direction, percent: Float, speed: Int)/*Boolean*/{}
    /**
    */
//    fun <U> scrollUntil(direction: Direction, condition: Condition<Object, U>)/*: U {}*/{}
    /**
     */
    fun <U> scrollUntil(direction: Direction, condition: EventCondition<U>)/*U*/{}

    /**
     */
    fun setGestureMargin(margin: Int) {}

    /**
     */
    fun setGestureMarginPercentage(percent: Float) {}

    /**
     */
    fun setGestureMargins(left: Int, top: Int, right: Int, bottom: Int) {}

    /**
     */
    fun setGestureMarginsPercentage(left: Float, top: Float, right: Float, bottom: Float) {}

    /**
     */
    fun setText(text: String?) {}

    /**
     */
    fun swipe(direction: Direction, percent: Float) {}

    /**
     */
    fun swipe(direction: Direction, percent: Float, speed: Int) {}
    //fun <U>wait(condition:Condition<Object,U>,timeout:Long):U{}*/{}
    /**
     */
    fun <U> wait(condition: SearchCondition<U>)/*U*/{}

    /**
     */
    fun <U> wait(condition: UiObject2Condition<U>, timeout: Long)/*U*/{}
}
