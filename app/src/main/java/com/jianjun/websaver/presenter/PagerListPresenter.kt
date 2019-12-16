package com.jianjun.websaver.presenter

import com.jianjun.websaver.base.mvp.BasePresenter
import com.jianjun.websaver.contact.PagerListContact
import com.jianjun.websaver.module.IPagerDbModel
import com.jianjun.websaver.module.PagerDbModel
import com.jianjun.websaver.utils.flowableToMain
import com.jianjun.websaver.utils.observableToMain
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/**
 * Created by jianjunhuang on 11/26/19.
 */
class PagerListPresenter :
    BasePresenter<PagerListContact.IPagersView, IPagerDbModel>() {

    override fun createModel(): IPagerDbModel {
        return PagerDbModel()
    }

    open fun queryPagers() {
        getModel().queryPagers()?.compose(flowableToMain())?.subscribe(Consumer {
            getView().onPagers(pagers = it)
        })?.let { addDisposable(it) }
    }

}