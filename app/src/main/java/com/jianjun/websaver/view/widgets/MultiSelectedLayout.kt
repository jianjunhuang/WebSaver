package com.jianjun.websaver.view.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.jianjun.websaver.R
import com.jianjun.websaver.view.adapter.MultiSelectListAdapter

class MultiSelectedLayout(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private var selectAllCb: CheckBox
    private var selectedNumTV: TextView
    private var delImg: ImageView
    private var closeImg: ImageView
    var selectedListener: MultiSelectedListener? = null
    private var adapter: MultiSelectListAdapter<*, *>? = null
    private var isSelectedAll = false

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_multi_select, this, true)
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
            selectedListener?.onDeleted()
            adapter?.delItem()
        }
        closeImg.setOnClickListener {
            selectedListener?.onClose()
            dismiss()
            adapter?.showCheckBox(false)
        }
        dismiss()
    }

    fun setSelectAdapter(adapter: MultiSelectListAdapter<*, *>) {
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
    }

    fun dismiss() {
        visibility = View.GONE
    }

    fun show() {
        visibility = View.VISIBLE
    }

    interface MultiSelectedListener {
        fun onClose()
        fun onDeleted()
        fun onSelectedAll(selectedAll: Boolean)
    }
}