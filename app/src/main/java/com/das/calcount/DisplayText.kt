package com.das.calcount

import android.widget.TextView

class DisplayText(private val activity: MainActivity) {
    private val display by lazy {
        activity.findViewById<TextView>(R.id.caloryCount)
    }

    fun setText(calNum: Float) {
        if (calNum % 1 == 0f) {
            display.text = activity.getString(R.string.display_int_text, calNum.toInt())
        }
        else {
            display.text = activity.getString(R.string.display_float_text, calNum)
        }
    }
}