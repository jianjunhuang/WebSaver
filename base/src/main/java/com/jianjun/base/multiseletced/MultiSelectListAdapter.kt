package com.jianjun.base.multiseletced

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.jianjun.base.R
import com.jianjun.base.adapter.BaseAdapter

abstract class MultiSelectListAdapter<D, V : MultiSelectListAdapter.MultiSelectViewHolder> :
    BaseAdapter<D, V>(), View.OnLongClickListener {

    open var checkedList: MutableList<Boolean> = ArrayList()
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
        if (checkedList.isNotEmpty()) {
            holder.checkBox.isChecked = checkedList[position]
        }
        holder.checkBox.setOnClickListener(checkboxOnClickListener)
        holder.checkBox.tag = position
        holder.itemView.setOnLongClickListener(this)
        super.onBindViewHolder(holder, position)
    }

    fun selectAll(selectAll: Boolean) {
        data.forEachIndexed { index, b ->
            checkedList[index] = selectAll
        }
        notifyItemRangeChanged(0, data.size)
        onSelectedListener?.onSelectedRate(itemCount, checkedList.count { it })
    }

    fun delItem() {
        val iterator = checkedList.iterator()
        val dataIterator = data.iterator()
        while (iterator.hasNext() && dataIterator.hasNext()) {
            val selected = iterator.next()
            dataIterator.next()
            if (selected) {
                iterator.remove()
                dataIterator.remove()
            }
        }
        notifyDataSetChanged()
    }


    fun showCheckBox(show: Boolean) {
        showCheckBox = show
        onSelectedListener?.onShow(show)
        if (!show) {
            for ((index, check) in checkedList.withIndex())
                checkedList[index] = false
        }
        notifyDataSetChanged()
    }

    override fun onLongClick(v: View?): Boolean {
        showCheckBox(true)
        return true
    }

    override fun refresh(data: List<D>) {
        checkedList.clear()
        repeat(data.size) { checkedList.add(false) }
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

            if (checkedList.size < pos) {
                checkedList.add(pos, checkBox.isChecked)
            } else {
                checkedList[pos] = checkBox.isChecked
            }
            selectedListenerCallback(checkBox, pos)
        }
    }

    fun selectedListenerCallback(checkBox: CheckBox, pos: Int) {
        onSelectedListener?.onSelected(checkBox, pos, checkBox.isChecked)
        onSelectedListener?.onSelectedRate(itemCount, checkedList.count { it })
    }

    interface OnSelectedListener {
        fun onSelected(checkBox: CheckBox, pos: Int, selected: Boolean)
        fun onShow(show: Boolean)
        fun onSelectedRate(size: Int, selected: Int)
    }
}