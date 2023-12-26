package com.example.myapplication

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.TextView

class CustomTextView : androidx.appcompat.widget.AppCompatTextView {
    constructor(context: Context?) : super(context!!) {
        minimumWidth = 1
        minimumHeight = 1
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        minimumWidth = 1
        minimumHeight = 1
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!,
        attrs,
        defStyle
    ) {
        minimumWidth = 1
        minimumHeight = 1
    }

    override fun onDraw(canvas: Canvas) {
        // Do nothing
    }
}