package com.jianjun.websaver.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by jianjunhuang on 11/26/19.
 */
interface ItemClickListener<DATA> {
    fun onItemClick(data: DATA, view: View, position: Int, adapter: RecyclerView.Adapter<*>)
}