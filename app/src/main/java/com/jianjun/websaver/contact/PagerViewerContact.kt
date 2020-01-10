package com.jianjun.websaver.contact

import com.jianjun.websaver.base.mvp.IModel
import com.jianjun.websaver.base.mvp.IView
import com.jianjun.websaver.module.db.entity.Pager
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Created by jianjunhuang on 11/24/19.
 */
interface PagerViewerContact {
    interface IViewerView : IView {
        fun onPagerSaved()
        fun onPagerSavedError(reason: String)
        fun onStateUpdate(pager: Pager?)
        fun onPagerPosUpdate(position: Int)
    }
}