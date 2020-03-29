package com.jianjun.websaver.view.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.widget.NestedScrollView
import com.jianjun.websaver.R
import com.jianjun.base.mvp.BaseMvpActivity
import com.jianjun.websaver.contact.PagerViewerContact
import com.jianjun.websaver.databinding.ActivityViewerBinding
import com.jianjun.websaver.module.db.entity.Pager
import com.jianjun.websaver.presenter.PagerViewerPresenter
import com.jianjun.websaver.view.fragment.PagerViewerBottomFragment
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
        showSnack(databinding.webView, "Save Successfully")
    }

    override fun onPagerSavedError(reason: String) {
        showSnack(databinding.webView, reason)
    }

    override fun onStateUpdate(pager: Pager?) {
        if (pager == null) {
            //todo
        } else {
            databinding.ivReadState.setImageResource(if (pager.isRead) R.drawable.ic_done_all else R.drawable.ic_done)
        }
    }

    override fun onPagerPosUpdate(position: Int) {
        databinding.scrollView.scrollTo(0, position)
    }


    override fun createPresenter(): PagerViewerPresenter? {
        return PagerViewerPresenter()
    }

    private var title: String? = null
    private var url: String? = null
    private var referrer: String? = null
    private lateinit var databinding: ActivityViewerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databinding = ActivityViewerBinding.inflate(layoutInflater)
        setSupportActionBar(databinding.toolbar)
        databinding.ivSave.setOnClickListener(this)
        databinding.ivClose.setOnClickListener(this)
        databinding.ivReadState.setOnClickListener(this)
        databinding.ivMore.setOnClickListener(this)
        databinding.scrollView.setOnScrollChangeListener(this)

        setupWebView()

        url = if (Intent.ACTION_VIEW == intent.action)
            intent.data.toString()
        else
            intent.getStringExtra(Intent.EXTRA_TEXT)

        databinding.webView.loadUrl(url.toString())

        referrer = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            getReferrer()?.authority
        } else {
            intent.getStringExtra(Intent.EXTRA_REFERRER)
        }
        databinding.toolbar.subtitle = "From $referrer"
        getPresenter()?.loadCache(url.toString())

    }

    private fun setupWebView() {
        val listener = object : InterWebListener {
            override fun showTitle(title: String?) {
                databinding.toolbar.title = title
                this@PagerViewerActivity.title = title
            }

            override fun hindProgressBar() {
                databinding.progress.visibility = View.GONE
            }

            override fun startProgress(newProgress: Int) {
                databinding.progress.progress = newProgress
            }

            override fun showErrorView(type: Int) {

            }

        }
        val webViewClient = MWebViewClient(databinding.webView, this)
        webViewClient.setWebListener(listener)
        webViewClient.listener = object : MWebViewClient.Callback {
            override fun onStart() {
            }

            override fun onStop() {
                getPresenter()?.webViewLoadFinish()
            }

        }
        databinding.webView.webViewClient = webViewClient
        val webChromeClient = MWebViewChromeClient(databinding.webView, this)
        webChromeClient.setWebListener(listener)
        webChromeClient.setOnReceivedIconListener(object :
            MWebViewChromeClient.OnReceivedIconListener {
            override fun onReceivedIcon(webView: WebView?, bitmap: Bitmap?) {
                databinding.toolbar.logo = BitmapDrawable(resources, bitmap)
            }

        })
        databinding.webView.webChromeClient = webChromeClient
    }

    override fun onBackPressed() {
        if (databinding.webView?.canGoBack()!!) {
            databinding.webView.goBack()
        } else {
            ActivityCompat.finishAfterTransition(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        databinding.webView.let {
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
                    getPresenter()?.updateReadState()
                }
            }
            R.id.iv_more -> {
                url?.let {
                    PagerViewerBottomFragment.newInstance(it)
                        .show(supportFragmentManager, PagerViewerBottomFragment.TAG)
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