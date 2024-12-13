package com.rocky.baselib.webview

import android.util.Log
import android.webkit.DownloadListener

open class SimpleDownloadListener : DownloadListener {
    override fun onDownloadStart(
        url: String?,
        userAgent: String?,
        contentDisposition: String?,
        mimetype: String?,
        contentLength: Long
    ) {
        Log.d(
            "webview",
            """
                onDownloadStart, url=$url, 
                userAgent=$userAgent, 
                contentDisposition=$contentDisposition, 
                mimetype=$mimetype, 
                contentLength=$contentLength
            """.trimIndent()
        )
    }
}