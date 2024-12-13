package com.rocky.baselib.webview

import android.net.http.SslError
import android.os.Build
import android.util.Log
import android.webkit.SslErrorHandler
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient

open class SimpleWebViewClient : WebViewClient() {

    override fun shouldInterceptRequest(
        view: WebView?,
        request: WebResourceRequest?
    ): WebResourceResponse? {

        Log.d(
            "webview",
            """
                    shouldInterceptRequest, url: ${request?.url}, 
                    requestHeaders: ${request?.requestHeaders},
                    isRedirect: ${
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    request?.isRedirect
                } else false
            }
                """.trimIndent()
        )

        return super.shouldInterceptRequest(view, request).also { response ->
//            view ?: return@also
//            response?.data?.let {
//                IOUtils.copy(
//                    it,
//                    FileOutputStream(File(view.context.filesDir, "${request?.url.hashCode()}"))
//                )
//            }
        }
    }

    override fun onReceivedSslError(
        view: WebView?,
        handler: SslErrorHandler?,
        error: SslError?
    ) {
        handler?.proceed()
    }
}