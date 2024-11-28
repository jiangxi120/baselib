package com.rocky.baselib.ext

import android.content.Context
import androidx.core.content.ContextCompat

fun Int.resString(context: Context, mate: String? = null): String {
    val resources = context.resources
    return if (mate == null) {
        resources?.getString(this) ?: ""
    } else {
        resources?.getString(this, mate) ?: ""
    }
}

fun Int.resString(context: Context, vararg formatArgs: Any?): String {
    val resources = context.resources
    return if (formatArgs.isEmpty()) {
        resources?.getString(this) ?: ""
    } else {
        resources?.getString(this, *formatArgs) ?: ""
    }
}

fun Int.resColor(context: Context): Int {
    return ContextCompat.getColor(context, this@resColor)
}

fun Int.resDimen(context: Context): Int {
    return context.resources?.getDimension(this)?.toInt() ?: 0
}