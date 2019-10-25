package com.jianjun.websaver.view.widgets

import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.jianjun.websaver.R

/**
 * Created by jianjunhuang on 10/22/19.
 */
class BottomNavigationView(context: Context?, attrs: AttributeSet? = null) :
    LinearLayout(context, attrs), View.OnClickListener {

    @ColorRes
    private val colorResDef: Int = R.color.colorNavDef

    @ColorRes
    private val colorResSelected: Int = R.color.colorNavSelected

    private val navs: ArrayList<ImageView> = ArrayList()

    private var selectedListener: OnSelectedListener? = null

    public fun setSelectedListener(selectedListener: OnSelectedListener) {
        this.selectedListener = selectedListener
    }

    override fun onClick(v: View?) {
        for (image in navs) {
            switchColor(image, colorResDef)
        }
        switchColor(v as ImageView, colorResSelected)
        selectedListener?.onSelected(v, v.getTag() as Int)
    }

    public fun select(pos: Int) {
        for (image in navs) {
            switchColor(image, colorResDef)
        }
        switchColor(navs[pos], colorResSelected)
    }

    private fun switchColor(image: ImageView, @ColorRes colorRes: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            image.imageTintList =
                ColorStateList.valueOf(ContextCompat.getColor(context, colorRes))
        }
    }

    fun addItem(vararg items: Navigation?): BottomNavigationView {
        var index = childCount
        for (item in items) {
            val image = ImageView(context)
            image.setImageResource(item?.src!!)
            image.scaleType = ImageView.ScaleType.CENTER_INSIDE
            image.setOnClickListener(this)
            image.tag = index++
            val params = LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                resources.getDimension(R.dimen.bottom_nav_size).toInt(),
                1f
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                image.imageTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(context, colorResDef))
            }
            addView(image, params)
            navs.add(image)
        }
        select(0)
        return this
    }

    public interface OnSelectedListener {
        fun onSelected(view: ImageView, pos: Int)
    }
}