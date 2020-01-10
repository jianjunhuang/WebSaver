package com.jianjun.websaver.view.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.widget.NestedScrollView
import com.google.android.material.snackbar.Snackbar
import com.jianjun.websaver.R
import com.jianjun.websaver.base.mvp.BaseMvpActivity
import com.jianjun.websaver.contact.PagerViewerContact
import com.jianjun.websaver.module.db.entity.Pager
import com.jianjun.websaver.presenter.PagerViewerPresenter
import com.jianjun.websaver.webview.MWebViewChromeClient
import com.jianjun.websaver.webview.MWebViewClient
import com.tencent.smtt.sdk.WebView
import com.ycbjie.webviewlib.*

/**
 * Created by jianjunhuang on 11/13/19.
 */

class PagerViewerActivity :
    BaseMvpActivity<PagerViewerPresenter>(), PagerViewerContact.IViewerView, View.OnClickListener,
    NestedScrollView.OnScrollChangeListener {

    override fun onPagerSaved() {
        Snackbar.make(webview!!, "Save Successfully", Snackbar.LENGTH_SHORT).show()
    }

    override fun onPagerSavedError(reason: String) {
        Snackbar.make(webview!!, reason, Snackbar.LENGTH_SHORT).show()
    }

    override fun onStateUpdate(pager: Pager?) {
        if (pager == null) {
            //todo
        } else {
            readStateImg?.setImageResource(if (pager.isRead) R.drawable.ic_done_all else R.drawable.ic_done)
        }
    }

    override fun onPagerPosUpdate(position: Int) {
        scrollView?.scrollTo(0, position)
    }


    override fun createPresenter(): PagerViewerPresenter? {
        return PagerViewerPresenter()
    }

    private var webview: X5WebView? = null
    private var toolbar: Toolbar? = null
    private var progressBar: ProgressBar? = null
    private var title: String? = null
    private var url: String? = null
    private var referrer: String? = null
    private var readStateImg: ImageView? = null
    private var saveImg: ImageView? = null
    private var scrollView: NestedScrollView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewer)
        webview = findViewById(R.id.web_view)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        progressBar = findViewById(R.id.progress)
        saveImg = findViewById(R.id.iv_save)
        saveImg?.setOnClickListener(this)
        findViewById<ImageView>(R.id.iv_close).setOnClickListener(this)
        readStateImg = findViewById(R.id.iv_read_state)
        readStateImg?.setOnClickListener(this)
        scrollView = findViewById(R.id.scroll_view)
        scrollView?.setOnScrollChangeListener(this)

        setupWebView()

        url = if (Intent.ACTION_VIEW == intent.action)
            intent.data.toString()
        else
            intent.getStringExtra(Intent.EXTRA_TEXT)

        webview?.loadUrl(url.toString())

        referrer = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            getReferrer()?.authority
        } else {
            intent.getStringExtra(Intent.EXTRA_REFERRER)
        }
        toolbar?.subtitle = "From $referrer"
        getPresenter()?.loadCache(url.toString())

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
        val webViewClient = MWebViewClient(webview, this)
        webViewClient.setWebListener(listener)
        webViewClient.listener = object : MWebViewClient.Callback {
            override fun onStart() {
            }

            override fun onStop() {
                getPresenter()?.webViewLoadFinish()
            }

        }
        webview?.webViewClient = webViewClient
        val webChromeClient = MWebViewChromeClient(webview, this)
        webChromeClient.setWebListener(listener)
        webChromeClient.setOnReceivedIconListener(object :
            MWebViewChromeClient.OnReceivedIconListener {
            override fun onReceivedIcon(webView: WebView?, bitmap: Bitmap?) {
                toolbar?.logo = BitmapDrawable(resources, bitmap)
            }

        })
        webview?.webChromeClient = webChromeClient
    }

    override fun onBackPressed() {
        if (webview?.canGoBack()!!) {
            webview?.goBack()
        } else {
            ActivityCompat.finishAfterTransition(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        webview?.let {
            val viewGroup = it.parent as ViewGroup
            viewGroup.removeView(it)
            it.stopLoading()
            it.destroy()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_close -> {
                ActivityCompat.finishAfterTransition(this)
            }
            R.id.iv_save -> {
                url?.let {
                    getPresenter()?.savePager(it, title, referrer)
                }
            }
            R.id.iv_read_state -> {
                url?.let {
                    getPresenter()?.updateReadState(it, title, referrer)
                }
            }
        }
    }

    override fun onScrollChange(
        v: NestedScrollView?,
        scrollX: Int,
        scrollY: Int,
        oldScrollX: Int,
        oldScrollY: Int
    ) {
        getPresenter()?.updateScrollPos(scrollY)
    }

}