package com.jianjun.websaver.webview

import android.content.Context
import android.graphics.Bitmap
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.sdk.WebView
import com.ycbjie.webviewlib.BridgeWebView
import com.ycbjie.webviewlib.X5WebViewClient

class MWebViewClient(webView: BridgeWebView?, context: Context?) :
    X5WebViewClient(webView, context) {

    private var isFinish = false
    open var listener: Callback? = null

    override fun onPageStarted(webView: WebView?, s: String?, bitmap: Bitmap?) {
        super.onPageStarted(webView, s, bitmap)
        isFinish = true
        listener?.onStart()
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        isFinish = false
        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        isFinish = false
        return super.shouldOverrideUrlLoading(view, url)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        if (isFinish) {
            listener?.onStop()
        }
    }

    interface Callback {
        fun onStart()
        fun onStop()
    }

}