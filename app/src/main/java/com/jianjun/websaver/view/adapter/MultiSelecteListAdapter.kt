package com.jianjun.websaver.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.jianjun.websaver.R

abstract class MultiSelecteListAdapter<V : MultiSelecteListAdapter.MultiSelectViewHolder> :
    RecyclerView.Adapter<V>(), CompoundButton.OnCheckedChangeListener {

    open var checkedList: ArrayList<Boolean> = ArrayList(itemCount)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): V {
        val layout =
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_selecter,
                parent,
                false
            ) as ViewGroup
        return createViewHolder(layout, viewType)
    }

    abstract fun createMultiSelectViewHolder(parent: ViewGroup, viewType: Int): V

    override fun onBindViewHolder(holder: V, position: Int) {
        holder.checkBox.isChecked = checkedList[position]
        holder.checkBox.setOnCheckedChangeListener(this)
        holder.checkBox.tag = position
    }

    fun selectAll(selectAll: Boolean) {
        checkedList.forEachIndexed { index, b ->
            checkedList[index] = selectAll
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        val pos: Int = buttonView?.tag as Int
        checkedList[pos] = isChecked
    }

    open class MultiSelectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.cb_select)
    }
}