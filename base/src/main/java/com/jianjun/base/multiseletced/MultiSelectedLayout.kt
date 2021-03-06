package com.jianjun.base.multiseletced

import android.content.Context
import android.util.ArrayMap
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.jianjun.base.R

class MultiSelectedLayout<D>(context: Context, attrs: AttributeSet?) :
    FrameLayout(context, attrs) {

    private var selectAllCb: CheckBox
    private var selectedNumTV: TextView
    private var delImg: ImageView
    private var closeImg: ImageView
    var selectedListener: MultiSelectedListener<D>? = null
        set(listener) {
            field = listener
            adapter?.multiSelectedListener = listener
        }
    private var adapter: MultiSelectListAdapter<D, *>? = null
    private var isSelectedAll = false

    init {
        LayoutInflater.from(context).inflate(R.layout.base_layout_multi_select, this, true)
        selectAllCb = findViewById(R.id.cb_select_all)
        selectedNumTV = findViewById(R.id.tv_selected_num)
        delImg = findViewById(R.id.iv_del)
        closeImg = findViewById(R.id.iv_close)

        selectAllCb.setOnCheckedChangeListener { buttonView, isChecked ->
            //isSelectedAll = false && isChecked = true -> 全选
            //isSelectedAll = true && isChecked = false -> 全不选
            if (isSelectedAll != isChecked) {
                selectedListener?.onSelectedAll(isChecked)
                adapter?.selectAll(isChecked)
            }
            isSelectedAll = isChecked
        }

        delImg.setOnClickListener {
            if (selectedListener == null) {
                adapter?.delItem()
            }
            selectedListener?.let { listener ->
                adapter?.let {
                    if (!listener.onDeletedClicked(it.checkedItems)) {
                        adapter?.delItem()
                    }
                }
            }
        }
        closeImg.setOnClickListener {
            close()
        }
        dismiss()
    }

    private fun close() {
        selectedListener?.onClose()
        dismiss()
        adapter?.showCheckBox(false)
    }


    fun setSelectAdapter(adapter: MultiSelectListAdapter<D, *>) {
        this.adapter = adapter
        adapter.onSelectedListener = object : MultiSelectListAdapter.OnSelectedListener {
            override fun onSelected(checkBox: CheckBox, pos: Int, selected: Boolean) {
                if (!selected) {
                    isSelectedAll = false
                    //有一个取消了，全选的框框也取消
                    selectAllCb.isChecked = false
                }
            }

            override fun onShow(show: Boolean) {
                if (show) show() else dismiss()
            }

            override fun onSelectedRate(size: Int, selected: Int) {
                selectedNumTV.text = "$selected / $size"
                if (size == selected && size != 0) {
                    selectAllCb.isChecked = true
                }
            }

        }
        selectedListener?.let {
            adapter.multiSelectedListener = selectedListener
        }
    }

    fun dismiss() {
        visibility = View.GONE
    }

    fun show() {
        visibility = View.VISIBLE
    }

    /**
     * delete selected item
     */
    fun delete() {
        adapter?.delItem()
    }

    interface MultiSelectedListener<D> {
        fun onClose()
        fun onDeletedClicked(checkItems: ArrayMap<Int, D>): Boolean
        fun onRealDeleted(checkItems: ArrayMap<Int, D>)
        fun onSelectedAll(selectedAll: Boolean)
        fun interceptDelete(checkItems: ArrayMap<Int, D>): Boolean
    }
}