package com.jianjun.websaver.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.jianjun.websaver.R
import com.jianjun.websaver.base.ItemClickListener
import com.jianjun.websaver.module.db.entity.Pager
import kotlinx.android.synthetic.main.item_pager.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Created by jianjunhuang on 11/26/19.
 */
class PagerListAdapter : RecyclerView.Adapter<PagerListAdapter.PagersViewHolder> {

    private var pagerList: List<Pager>
    private val dateFormatter = SimpleDateFormat("yyyy/MM/dd HH:mm")
    private var onItemClickListener: ItemClickListener<Pager>? = null

    constructor(pagerList: List<Pager>?) {
        this.pagerList = pagerList ?: ArrayList<Pager>()
    }

    open fun setOnItemCLickListener(onItemClickListener: ItemClickListener<Pager>) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PagerListAdapter.PagersViewHolder {
        return PagersViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_pager,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return pagerList.size
    }

    open fun refresh(list: List<Pager>) {
        this.pagerList = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: PagerListAdapter.PagersViewHolder, position: Int) {
        val pager = pagerList[position]
        holder.title.text = pager.title
        holder.date.text = dateFormatter.format(Date(pager.createDate))
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(
                pager,
                position,
                this
            )
        }
        val context = holder.itemView.context
        if (pager.isRead) {
            val colorRead = ContextCompat.getColor(context, R.color.colorRead)
            holder.date.setTextColor(colorRead)
            holder.title.setTextColor(colorRead)
        } else {
            val colorUnRead = ContextCompat.getColor(context, R.color.colorUnRead)
            holder.date.setTextColor(colorUnRead)
            holder.title.setTextColor(colorUnRead)
        }
    }

    class PagersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.tv_title)
        var date: TextView = itemView.findViewById(R.id.tv_date)
    }
}