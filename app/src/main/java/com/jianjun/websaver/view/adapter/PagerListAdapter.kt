package com.jianjun.websaver.view.adapter

import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.jianjun.base.adapter.MultiSelectListAdapter
import com.jianjun.websaver.R
import com.jianjun.base.base.ItemClickListener
import com.jianjun.websaver.module.db.entity.Pager
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by jianjunhuang on 11/26/19.
 */
class PagerListAdapter :
    MultiSelectListAdapter<Pager, PagerListAdapter.PagersViewHolder>() {

    private val dateFormatter = SimpleDateFormat("yyyy/MM/dd HH:mm")
    private var onItemClickListener: ItemClickListener<Pager>? = null

    fun setOnItemCLickListener(onItemClickListener: ItemClickListener<Pager>) {
        this.onItemClickListener = onItemClickListener
    }

    class PagersViewHolder(id: Int, parent: ViewGroup) : MultiSelectViewHolder(id, parent) {
        var title: TextView = itemView.findViewById(R.id.tv_title)
        var date: TextView = itemView.findViewById(R.id.tv_date)
    }

    override fun createMultiSelectViewHolder(parent: ViewGroup, viewType: Int): PagersViewHolder {
        return PagersViewHolder(
            R.layout.item_pager,
            parent
        )
    }

    override fun convert(holder: PagersViewHolder, data: Pager, position: Int) {
        holder.title.text = data.title
        holder.date.text = dateFormatter.format(Date(data.createDate))
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(
                data,
                it,
                position,
                this
            )
        }
        val context = holder.itemView.context
        if (data.isRead) {
            val colorRead = ContextCompat.getColor(context, R.color.colorRead)
            holder.date.setTextColor(colorRead)
            holder.title.setTextColor(colorRead)
        } else {
            val colorUnRead = ContextCompat.getColor(context, R.color.colorUnRead)
            holder.date.setTextColor(colorUnRead)
            holder.title.setTextColor(colorUnRead)
        }
    }
}