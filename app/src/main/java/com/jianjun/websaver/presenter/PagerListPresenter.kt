package com.jianjun.websaver.presenter

import com.jianjun.base.mvp.BasePresenter
import com.jianjun.websaver.contact.PagerListContact
import com.jianjun.websaver.module.*
import com.jianjun.base.utils.flowableToMain

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