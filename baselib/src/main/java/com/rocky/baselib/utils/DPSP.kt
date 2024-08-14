package com.rocky.baselib.utils

import android.content.res.Resources
import android.util.TypedValue

val Int.dp: Int
    get() {
        return this.toFloat().dp
    }
val Float.dp: Int
    get() {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics).toInt()
    }


val Int.sp: Float
    get() {
        return this.toFloat().sp
    }
val Float.sp: Float
    get() {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, this, Resources.getSystem().displayMetrics)
    }