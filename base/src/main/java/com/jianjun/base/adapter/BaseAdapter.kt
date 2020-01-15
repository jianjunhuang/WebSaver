package com.jianjun.base.adapter

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<D, V : RecyclerView.ViewHolder> : RecyclerView.Adapter<V>() {

    var data: MutableList<D> = ArrayList<D>()

    open fun refresh(data: List<D>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    open fun append(data: List<D>) {
        val start = this.data.size
        this.data.addAll(data)
        notifyItemRangeChanged(start, this.data.size)
    }

    override fun onBindViewHolder(holder: V, position: Int) {
        convert(holder, data[position], position)
    }

    abstract fun convert(holder: V, data: D, position: Int)

    override fun getItemCount(): Int {
        return data.size
    }
}