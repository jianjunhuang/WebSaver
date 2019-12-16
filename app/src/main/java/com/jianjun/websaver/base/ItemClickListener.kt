package com.jianjun.websaver.base

import androidx.recyclerview.widget.RecyclerView

/**
 * Created by jianjunhuang on 11/26/19.
 */
interface ItemClickListener<DATA> {
    fun onItemClick(data: DATA, position: Int, adapter: RecyclerView.Adapter<*>)
}