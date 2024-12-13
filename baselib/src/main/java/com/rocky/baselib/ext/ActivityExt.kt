package com.rocky.baselib.ext

import android.app.Activity
import android.content.Intent

fun Activity.startActivity(cls: Class<out Activity>) {
    this.startActivity(Intent(this, cls))
}