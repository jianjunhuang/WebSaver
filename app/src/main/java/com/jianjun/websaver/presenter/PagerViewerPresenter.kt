package com.jianjun.websaver.presenter

import com.jianjun.websaver.base.mvp.BasePresenter
import com.jianjun.websaver.contact.PagerViewerContact
import com.jianjun.websaver.module.IPagerDbModel
import com.jianjun.websaver.module.PagerDbModel
import com.jianjun.websaver.module.db.entity.Pager
import com.jianjun.websaver.utils.HLog
import com.jianjun.websaver.utils.completableToMain
import com.jianjun.websaver.utils.flowableToMain
import java.util.*

/**
 * Created by jianjunhuang on 11/24/19.
 */
class PagerViewerPresenter :
    BasePresenter<PagerViewerContact.IViewerView, IPagerDbModel>() {

    private var pager: Pager? = null
    private var isRead = false

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
            p.source = source
            p.isRead = isRead
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

    fun updateReadState(url: String, title: String?, source: String?) {
        this.isRead = !isRead
        savePager(url, title, source)
    }
}