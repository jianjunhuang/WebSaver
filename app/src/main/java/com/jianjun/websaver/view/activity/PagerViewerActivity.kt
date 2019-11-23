package com.jianjun.websaver.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.room.util.StringUtil
import com.jianjun.websaver.R
import com.jianjun.websaver.base.BaseActivity
import com.ycbjie.webviewlib.*

/**
 * Created by jianjunhuang on 11/13/19.
 */

class PagerViewerActivity : BaseActivity() {

    var webview: X5WebView? = null
    var toolbar: Toolbar? = null
    var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewer)
        webview = findViewById(R.id.web_view)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        progressBar = findViewById(R.id.progress)

        setupWebView()

        val url = intent?.getStringExtra(Intent.EXTRA_TEXT)

        webview?.loadUrl(url.toString())
    }

    private fun setupWebView() {
        val listener = object : InterWebListener {
            override fun showTitle(title: String?) {
                toolbar?.title = title
            }

            override fun hindProgressBar() {
                progressBar?.visibility = View.GONE
            }

            override fun startProgress(newProgress: Int) {
                progressBar?.progress = newProgress
            }

            override fun showErrorView(type: Int) {

            }

        }
        webview?.x5WebChromeClient?.setWebListener(listener)
        webview?.x5WebViewClient?.setWebListener(listener)
    }

    override fun onBackPressed() {
        if (webview?.canGoBack()!!) {
            webview?.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        webview?.destroy()
    }

    override fun onResume() {
        super.onResume()

    }

}