package com.jianjun.websaver.presenter

import com.jianjun.websaver.base.mvp.BasePresenter
import com.jianjun.websaver.contact.HomeContact
import com.jianjun.websaver.module.*
import io.reactivex.Observable

/**
 * Created by jianjunhuang on 1/7/20.
 */
class HomePresenter : BasePresenter<HomeContact.IHomeView, IPagerDbModel>() {
    override fun createModel(): IPagerDbModel {
        return PagerDbModel()
    }

    public fun queryTags() {
        Observable.fromArray(TAG_ALL, TAG_UNREAD, TAG_READ)
            .toList()
            .toObservable()
            .subscribe {
                getView().onTags(it)
            }.let { addDisposable(it) }

    }

}