package com.jianjun.websaver.contact

import com.jianjun.base.mvp.IView
import com.jianjun.websaver.module.db.entity.Pager

/**
 * Created by jianjunhuang on 11/26/19.
 */
interface PagerListContact {
    interface IPagersView : IView {
        fun onPagers(pagers: List<Pager>?)
    }

}