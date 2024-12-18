package com.rocky.baselib.ext

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.InputStream

fun Uri.toBitmap(context: Context): Bitmap? {
    val inputStream: InputStream? = context.contentResolver.openInputStream(this)
    return inputStream?.let { BitmapFactory.decodeStream(it) }
}