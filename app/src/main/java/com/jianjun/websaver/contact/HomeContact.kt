package com.jianjun.websaver.contact

import com.jianjun.websaver.base.mvp.IView

/**
 * Created by jianjunhuang on 1/7/20.
 */
interface HomeContact {
    interface IHomeView : IView {
        fun onTags(tags: List<String>)
    }
}