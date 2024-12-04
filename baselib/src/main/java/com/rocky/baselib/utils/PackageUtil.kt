package com.rocky.baselib.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.text.TextUtils

object PackageUtil {
    fun launchAppByUri(context: Context?, packageName: String?, uri: String?): Boolean {
        if (context == null) {
            return false
        }
        val intent = Intent(Intent.ACTION_VIEW)
        if (!TextUtils.isEmpty(packageName)) {
            intent.setPackage(packageName)
        }
        if (!TextUtils.isEmpty(uri)) {
            intent.setData(Uri.parse(uri))
        }

        if (isExistIntent(context, intent)) {
            try {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
                return true
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return false
    }

    fun isExistIntent(context: Context, intent: Intent?): Boolean {
        val packageManager = context.packageManager
        val list = packageManager.queryIntentActivities(intent!!, PackageManager.GET_ACTIVITIES)
        return list != null && list.size > 0
    }
}