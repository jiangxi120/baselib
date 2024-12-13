package com.rocky.demo

import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rocky.baselib.webview.SimpleDownloadListener
import com.rocky.baselib.webview.SimpleWebChromeClient
import com.rocky.baselib.webview.SimpleWebView
import com.rocky.baselib.webview.SimpleWebViewClient
import com.rocky.demo.databinding.ActivityTestWebviewBinding
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL
import org.apache.commons.io.IOUtils

class TestWebViewActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityTestWebviewBinding.inflate(layoutInflater)
    }
    private var webView: SimpleWebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.webViewContainer.addView(
            SimpleWebView(this).apply {
                webView = this
                this.webViewClient = object: SimpleWebViewClient() {

                }
                this.webChromeClient = object: SimpleWebChromeClient() {
                    override fun onReceivedTitle(view: WebView?, title: String?) {
                        super.onReceivedTitle(view, title)
                        binding.toolbar.title = title
                    }
                }
                this.setDownloadListener(object: SimpleDownloadListener() {
                    override fun onDownloadStart(
                        url: String?,
                        userAgent: String?,
                        contentDisposition: String?,
                        mimetype: String?,
                        contentLength: Long
                    ) {
                        super.onDownloadStart(
                            url,
                            userAgent,
                            contentDisposition,
                            mimetype,
                            contentLength
                        )
                        if (url!= null) {
                            try {
                                val uri = Uri.parse(url)
                                // Create a URL object
                                val fileUrl = URL(url)
                                // Open a connection to the URL and get the input stream
                                val inputStream = fileUrl.openStream()
                                // Create a FileOutputStream to write the downloaded data to a file
                                val file = File(this@TestWebViewActivity.filesDir, "${uri.lastPathSegment}")
                                val outputStream = FileOutputStream(file)
                                // Copy the data from the input stream to the output stream
                                IOUtils.copy(inputStream, outputStream)
                                // Close the streams
                                inputStream.close()
                                outputStream.close()
                            } catch (e: Exception) {
                                // Handle any exceptions that may occur during the download process
                                e.printStackTrace()
                            }
                        }
                    }
                })
//                loadUrl("https://www.baidu.com")
                loadUrl("http://192.168.2.224:8080")
            }
        )
    }

    override fun onBackPressed() {
        if (webView?.canGoBack() == true) {
            webView?.goBack()
            return
        }
        super.onBackPressed()
    }
}