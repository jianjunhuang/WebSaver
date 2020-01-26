package com.jianjun.websaver.presenter

import android.util.ArrayMap
import com.jianjun.base.mvp.BasePresenter
import com.jianjun.base.utils.completableToMain
import com.jianjun.websaver.contact.PagerListContact
import com.jianjun.websaver.module.*
import com.jianjun.base.utils.flowableToMain
import com.jianjun.websaver.module.db.entity.Pager

/**
 * Created by jianjunhuang on 11/26/19.
 */
class PagerListPresenter :
    BasePresenter<PagerListContact.IPagersView, IPagerDbModel>() {

    override fun createModel(): IPagerDbModel {
        return PagerDbModel()
    }

    fun queryPagers(tag: String) {
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

    fun deletePagers(checkedItems: ArrayMap<Int, Pager>) {
        getModel().deletePagers(checkedItems.values.toList())
            ?.compose(completableToMain())
            ?.subscribe({
                getView().onDeletedSuccess()
            }, { e ->
                getView().onDeletedFailed(e.toString())
            })?.let { addDisposable(it) }
    }
}