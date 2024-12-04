package com.rocky.demo

import android.content.Context
import com.rocky.baselib.utils.PackageUtil

fun testLaunchPkg(context: Context) {
//        PackageUtil.launchAppByUri(
//            context,
//            "com.android.vending",
//            "https://play.google.com/store/apps/details?id=com.muso.musicplayer"
//        )
        PackageUtil.launchAppByUri(
            context,
            null,
            "https://play.google.com/store/apps/details?id=com.muso.musicplayer"
        )
//        PackageUtil.launchAppByUri(
//            context,
//            "com.android.vending",
//            "market://details?id=com.muso.musicplayer"
//        )
//        PackageUtil.launchAppByUri(
//            context,
//            null,
//            "market://details?id=com.muso.musicplayer"
//        )
    }