package com.jianjun.websaver.presenter

import com.jianjun.base.mvp.BasePresenter
import com.jianjun.base.utils.HLog
import com.jianjun.base.utils.completableToMain
import com.jianjun.base.utils.flowableToMain
import com.jianjun.websaver.contact.PagerViewerContact
import com.jianjun.websaver.module.IPagerDbModel
import com.jianjun.websaver.module.PagerDbModel
import com.jianjun.websaver.module.db.entity.Pager
import java.util.*

/**
 * Created by jianjunhuang on 11/24/19.
 */
class PagerViewerPresenter :
    BasePresenter<PagerViewerContact.IViewerView, IPagerDbModel>() {

    var pager: Pager? = null
        private set
    private var isRead = false
    private var scrollPos = 0

    override fun createModel(): IPagerDbModel {
        return PagerDbModel()
    }

    fun loadCache(url: String) {
        getModel().getPagerByUrl(url)
            ?.compose(flowableToMain())
            ?.subscribe({
                pager = it
                isRead = it.isRead
                getView().onStateUpdate(it)
                HLog.i("Pager is save = $pager")
            }, {
                HLog.e(it.toString(), tr = it)
                getView().onStateUpdate(null)
            })?.let { addDisposable(it) }
    }

    fun savePager(url: String, title: String?, source: String?) {
        if (pager == null) {
            pager =
                Pager(url = url, title = title, createDate = Date().time, source = source)
        }
        pager?.let { p ->
            p.url = url
            p.title = title
            p.isRead = isRead
            p.position = scrollPos.toLong()
            getModel().savePager(p)?.compose(completableToMain())?.subscribe(
                {
                    getView().onPagerSaved()
                    getView().onStateUpdate(p)
                }
                , { error ->
                    error?.message?.let { getView().onPagerSavedError(it) }
                }
            )?.let { addDisposable(it) }
        }
    }

    fun updateReadState() {
        this.isRead = !isRead
        pager?.let {
            it.isRead = isRead
            getModel().savePager(it)?.compose(completableToMain())?.subscribe()
            getView().onStateUpdate(it)
        }
    }

    fun updateScrollPos(scrollPos: Int) {
        this.scrollPos = scrollPos
        HLog.i("scrollPos = $scrollPos")
    }

    fun webViewLoadFinish() {
        pager?.let {
            getView().onPagerPosUpdate(it.position.toInt())
        }
    }

    private fun updatePager() {
        pager?.let {
            getModel().savePager(it)?.compose(completableToMain())?.subscribe()
        }
    }

    private fun updateReadPos() {
        pager?.let { p ->
            p.position = scrollPos.toLong()
            updatePager()
        }
    }

    override fun onPause() {
        super.onPause()
        updateReadPos()
    }
}