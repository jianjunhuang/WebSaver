package com.jianjun.websaver.presenter

import com.jianjun.websaver.base.mvp.BasePresenter
import com.jianjun.websaver.contact.PagerListContact
import com.jianjun.websaver.module.*
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

    open fun queryPagers(tag: String) {
        val pagerFlowable = when (tag) {
            TAG_ALL -> getModel().queryPagers()
            TAG_READ -> getModel().queryPagerByReadStatus(true)
            TAG_UNREAD -> getModel().queryPagerByReadStatus(false)
            else -> getModel().queryPagersByTag(tag)
        }
        pagerFlowable?.compose(flowableToMain())?.subscribe {
            getView().onPagers(pagers = it)
        }?.let { addDisposable(it) }
    }

}