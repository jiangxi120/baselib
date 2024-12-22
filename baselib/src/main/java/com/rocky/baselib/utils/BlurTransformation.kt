package com.rocky.baselib.utils

import android.graphics.Bitmap
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.google.android.renderscript.Toolkit
import java.security.MessageDigest

class BlurTransformation(val radius: Int = 25) : BitmapTransformation() {
    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update("jp.wasabeef.glide.transformations.BlurTransformation.1${this.radius}".toByteArray())
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        return Toolkit.blur(toTransform, radius)
    }
}