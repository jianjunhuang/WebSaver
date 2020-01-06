package com.jianjun.websaver.presenter

import com.jianjun.websaver.base.mvp.BasePresenter
import com.jianjun.websaver.contact.PagerViewerContact
import com.jianjun.websaver.module.IPagerDbModel
import com.jianjun.websaver.module.PagerDbModel
import com.jianjun.websaver.module.db.entity.Pager
import com.jianjun.websaver.utils.HLog
import com.jianjun.websaver.utils.completableToMain
import com.jianjun.websaver.utils.flowableToMain
import com.jianjun.websaver.utils.observableToMain
import io.reactivex.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by jianjunhuang on 11/24/19.
 */
class PagerViewerPresenter :
    BasePresenter<PagerViewerContact.IViewerView, IPagerDbModel>() {

    private var pager: Pager = Pager("", "", "", "", 0)
    private var updated = false

    override fun createModel(): IPagerDbModel {
        return PagerDbModel()
    }

    fun loadCache(url: String) {
        getModel().getPagerByUrl(url)
            ?.compose(flowableToMain())
            ?.subscribe({
                pager = it
            }, {
                HLog.e(it.toString(), tr = it)
            })?.let { addDisposable(it) }
    }

    fun savePager(url: String, title: String?, source: String?) {
        pager.let {
            it.url = url
            it.title = title
            it.source = source
            it.createDate = Date().time
        }
        getModel().savePager(pager)?.compose(completableToMain())?.subscribe(
            { getView().onPagerSaved() }
            , { error ->
                error?.message?.let { getView().onPagerSavedError(it) }
            }
        )?.let { addDisposable(it) }
    }

    fun updateReadState() {
        if (updated) {
            return
        }
        updated = true
        Observable.interval(3, TimeUnit.SECONDS)
            .throttleFirst(3, TimeUnit.SECONDS)
            .observeOn(Schedulers.computation())
            .subscribeOn(Schedulers.computation())
            .flatMapCompletable {
                pager.isRead = true
                if (pager.url.isNotEmpty()) {
                    getModel().savePager(pager)
                } else {
                    Completable.error(Throwable(""))
                }
            }
            .subscribe {
                HLog.i("update read state")
            }.let { addDisposable(it) }
    }
}