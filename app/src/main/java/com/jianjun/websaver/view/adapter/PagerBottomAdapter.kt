package com.jianjun.websaver.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jianjun.base.adapter.BaseAdapter
import com.jianjun.websaver.R
import com.jianjun.websaver.databinding.ItemPagerBottomBinding
import com.jianjun.websaver.module.bean.PagerBottomBean

/**
 * Created by jianjunhuang on 1/27/20.
 */
class PagerBottomAdapter : BaseAdapter<PagerBottomBean, PagerBottomAdapter.ViewHolder>() {

    class ViewHolder(val dataBinding: ItemPagerBottomBinding) :
        RecyclerView.ViewHolder(dataBinding.root)

    override fun convert(holder: ViewHolder, data: PagerBottomBean, position: Int) {
        holder.apply {
            dataBinding.ivIcon.setImageResource(data.iconRes)
            dataBinding.tvTitle.text = data.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_pager_bottom,
                parent,
                false
            )
        )
    }
}