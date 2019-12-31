package com.jianjun.websaver.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar
import com.jianjun.websaver.R
import com.jianjun.websaver.base.mvp.BaseMvpActivity
import com.jianjun.websaver.contact.PagerViewerContact
import com.jianjun.websaver.presenter.PagerViewerPresenter
import com.ycbjie.webviewlib.*

/**
 * Created by jianjunhuang on 11/13/19.
 */

class PagerViewerActivity :
    BaseMvpActivity<PagerViewerPresenter>(), PagerViewerContact.IViewerView {

    override fun onPagerSaved() {
        Snackbar.make(webview!!, "Save Successfully", Snackbar.LENGTH_SHORT).show()
    }

    override fun onPagerSavedError(reason: String) {
        Snackbar.make(webview!!, reason, Snackbar.LENGTH_SHORT).show()
    }

    override fun createPresenter(): PagerViewerPresenter? {
        return PagerViewerPresenter()
    }

    var webview: X5WebView? = null
    var toolbar: Toolbar? = null
    var progressBar: ProgressBar? = null
    var title: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewer)
        webview = findViewById(R.id.web_view)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        progressBar = findViewById(R.id.progress)

        setupWebView()


        val url = if (Intent.ACTION_VIEW.equals(intent.action)) {
            intent.data.toString()
        } else {
            intent.getStringExtra(Intent.EXTRA_TEXT)
        }
        webview?.loadUrl(url.toString())

        findViewById<ImageView>(R.id.iv_save).setOnClickListener {
            if (url != null) {
                getPresenter()?.savePager(url, title, "")
            }
        }
    }

    private fun setupWebView() {
        val listener = object : InterWebListener {
            override fun showTitle(title: String?) {
                toolbar?.title = title
                this@PagerViewerActivity.title = title
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
        webview?.let {
            val viewgroup = it.parent as ViewGroup
            viewgroup.removeView(it)
            it.stopLoading()
            it.destroy()
        }
    }

    override fun onResume() {
        super.onResume()

    }

}