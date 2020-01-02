package com.jianjun.websaver.webview

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import com.jianjun.websaver.R
import com.tencent.smtt.sdk.WebView
import com.ycbjie.webviewlib.BridgeWebView
import com.ycbjie.webviewlib.X5WebChromeClient

class MWebViewChromeClient(webView: BridgeWebView?, activity: Activity?) :
    X5WebChromeClient(webView, activity) {

    private var iconListener: OnReceivedIconListener? = null

    override fun onReceivedIcon(p0: WebView?, p1: Bitmap?) {
        super.onReceivedIcon(p0, p1)
        p0?.let {
            iconListener?.onReceivedIcon(
                p0, p1 ?: BitmapFactory.decodeResource(p0.resources, R.mipmap.ic_launcher_round)
            )
        }
    }


    public fun setOnReceivedIconListener(iconListener: OnReceivedIconListener) {
        this.iconListener = iconListener
    }

    interface OnReceivedIconListener {
        fun onReceivedIcon(webView: WebView?, bitmap: Bitmap?)
    }

}