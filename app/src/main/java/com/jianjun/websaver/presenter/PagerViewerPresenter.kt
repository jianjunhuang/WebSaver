package com.jianjun.websaver.presenter

import com.jianjun.websaver.base.mvp.BasePresenter
import com.jianjun.websaver.contact.PagerViewerContact
import com.jianjun.websaver.module.IPagerDbModel
import com.jianjun.websaver.module.PagerDbModel
import com.jianjun.websaver.module.db.entity.Pager
import com.jianjun.websaver.utils.completableToMain
import com.jianjun.websaver.utils.flowableToMain
import com.jianjun.websaver.utils.observableToMain
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created by jianjunhuang on 11/24/19.
 */
class PagerViewerPresenter :
    BasePresenter<PagerViewerContact.IViewerView, IPagerDbModel>() {

    override fun createModel(): IPagerDbModel {
        return PagerDbModel()
    }

    fun savePager(url: String, title: String?, source: String?) {
        val pager: Pager = Pager(url, title, "", source, Date().time)
        getModel().savePager(pager)?.compose(completableToMain())?.subscribe(
            { getView().onPagerSaved() }
            , { error ->
                error?.message?.let { getView().onPagerSavedError(it) }
            }
        )?.let { addDisposable(it) }
    }
}