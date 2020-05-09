package com.jianjun.websaver.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jianjun.websaver.R
import com.jianjun.websaver.module.db.entity.Tag

/**
 * Created by jianjunhuang on 12/21/19.
 */

class TagsListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TAG_ITEM = 0
        const val TAG_ADD = 1
    }

    override
    fun getItemViewType(position: Int): Int = if (position == tags.size) TAG_ADD else TAG_ITEM


    private val tags: MutableList<Tag> = ArrayList<Tag>()

    public fun refresh(datas: MutableList<Tag>) {
        tags.clear()
        tags.addAll(datas)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if (viewType == TAG_ITEM) {
            TagsViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_tag,
                    parent,
                    false
                )
            )
        } else {
            TagAddViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_add,
                    parent,
                    false
                )
            )
        }

    override fun getItemCount(): Int = tags.size

    override
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TagsViewHolder -> {
                tags[position]?.let {
                    holder.tagTextView.text = "${it.name} ${it.size}"
                }
            }
            is TagAddViewHolder -> {

            }
        }
    }

    class TagsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tagTextView: TextView = itemView.findViewById(R.id.tv_tag)
    }

    class TagAddViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var addView: ImageView = itemView.findViewById(R.id.iv_add)
    }
}