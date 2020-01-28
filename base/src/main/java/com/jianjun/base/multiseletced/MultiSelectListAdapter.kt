package com.jianjun.base.multiseletced

import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.jianjun.base.R
import com.jianjun.base.adapter.BaseAdapter

abstract class MultiSelectListAdapter<D, V : MultiSelectListAdapter.MultiSelectViewHolder> :
    BaseAdapter<D, V>(), View.OnLongClickListener {

    internal val checkedItems: ArrayMap<Int, D> = ArrayMap()
    var onSelectedListener: OnSelectedListener? = null
    private var showCheckBox = false
    private val checkboxOnClickListener = CheckboxOnClickListener()
    internal var multiSelectedListener: MultiSelectedLayout.MultiSelectedListener<D>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): V {
        val layout =
            LayoutInflater.from(parent.context).inflate(
                R.layout.base_item_selecter,
                parent,
                false
            ) as ViewGroup
        return createMultiSelectViewHolder(layout, viewType)
    }

    abstract fun createMultiSelectViewHolder(parent: ViewGroup, viewType: Int): V

    override fun onBindViewHolder(holder: V, position: Int) {
        holder.checkBox.visibility = if (showCheckBox) View.VISIBLE else View.GONE
        if (checkedItems.isNotEmpty()) {
            holder.checkBox.isChecked = checkedItems.contains(position)
        }
        holder.checkBox.setOnClickListener(checkboxOnClickListener)
        holder.checkBox.tag = position
        holder.itemView.setOnLongClickListener(this)
        super.onBindViewHolder(holder, position)
    }

    fun selectAll(selectAll: Boolean) {
        if (selectAll) {
            data.forEachIndexed { index, data ->
                checkedItems[index] = data
            }
        } else {
            checkedItems.clear()
        }
        notifyItemRangeChanged(0, data.size)
        onSelectedListener?.onSelectedRate(itemCount, checkedItems.size)
    }

    private fun realDelItems() {
        val dataIterator = data.iterator()
        var index = 0
        multiSelectedListener?.let {
            it.onRealDeleted(checkedItems)
        }
        while (dataIterator.hasNext()) {
            dataIterator.next()
            if (checkedItems.containsKey(index)) {
                dataIterator.remove()
                checkedItems.remove(index)
            }
            index++
        }
        notifyDataSetChanged()
    }

    fun delItem() {
        if (multiSelectedListener == null) {
            realDelItems()
        }
        multiSelectedListener?.let {
            if (!it.interceptDelete(checkedItems)) {
                realDelItems()
            }
        }
        onSelectedListener?.onSelectedRate(itemCount, checkedItems.size)
    }


    fun showCheckBox(show: Boolean) {
        showCheckBox = show
        onSelectedListener?.onShow(show)
        if (!show) {
            checkedItems.clear()
        }
        notifyDataSetChanged()
    }

    override fun onLongClick(v: View?): Boolean {
        showCheckBox(true)
        return true
    }

    override fun refresh(data: List<D>) {
        checkedItems.clear()
        super.refresh(data)
    }

    open class MultiSelectViewHolder : RecyclerView.ViewHolder {
        constructor(id: Int, parent: ViewGroup) : super(parent) {
            val view = LayoutInflater.from(parent.context).inflate(id, parent, true)
        }

        val checkBox: CheckBox = itemView.findViewById(R.id.cb_select)
    }

    inner class CheckboxOnClickListener : View.OnClickListener {
        override fun onClick(v: View?) {
            val checkBox = v as CheckBox
            val pos: Int = v.tag as Int
            if (checkBox.isChecked) {
                checkedItems[pos] = data[pos]
            } else {
                checkedItems.remove(pos)
            }
            selectedListenerCallback(checkBox, pos)
        }
    }

    fun selectedListenerCallback(checkBox: CheckBox, pos: Int) {
        onSelectedListener?.onSelected(checkBox, pos, checkBox.isChecked)
        onSelectedListener?.onSelectedRate(itemCount, checkedItems.size)
    }

    interface OnSelectedListener {
        fun onSelected(checkBox: CheckBox, pos: Int, selected: Boolean)
        fun onShow(show: Boolean)
        fun onSelectedRate(size: Int, selected: Int)
    }
}