package com.jianjun.websaver.contact

import com.jianjun.websaver.base.mvp.IModel
import com.jianjun.websaver.base.mvp.IView
import com.jianjun.websaver.module.db.entity.Pager
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Created by jianjunhuang on 11/26/19.
 */
interface PagerListContact {
    interface IPagersView : IView {
        fun onPagers(pagers: List<Pager>?)
    }

}