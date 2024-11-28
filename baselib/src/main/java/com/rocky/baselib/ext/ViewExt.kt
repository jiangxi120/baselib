package com.rocky.baselib.ext

import android.view.View
import android.view.ViewGroup
import androidx.annotation.MainThread

private var lastClickMillis = 0L

@MainThread
fun View.setOnFastClickListener(listener: View.OnClickListener) {
    setOnClickListener {
        if (System.currentTimeMillis() - lastClickMillis > 500) {
            lastClickMillis = System.currentTimeMillis()
            listener.onClick(this)
        }
    }
}

fun View.xRelativeTo(relativeView: ViewGroup): Float {
    if (parent == null) {
        return 0f
    }
    return if (parent === relativeView) {
        x
    } else {
        x + (parent as ViewGroup).xRelativeTo(relativeView)
    }
}

fun View.yRelativeTo(relativeView: ViewGroup): Float {
    if (parent == null) {
        return 0f
    }
    return if (parent === relativeView) {
        y
    } else {
        y + (parent as ViewGroup).yRelativeTo(relativeView)
    }
}

val View.centerX: Float
    get() = x + width / 2f

val View.centerY: Float
    get() = y + height / 2f