package com.jianjun.websaver.view.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import com.jianjun.websaver.R

class TagView(context: Context?, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {

    private val bgPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        val values = context?.obtainStyledAttributes(attrs, R.styleable.TagView)
        values?.let {
            bgPaint.color = it.getColor(R.styleable.TagView_tagColor, Color.WHITE)
            it.recycle()
        }
    }

    public fun setTagColor(@ColorInt color: Int) {
        bgPaint.color = color
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawRect(0f, 0f, top.toFloat(), bottom.toFloat(), bgPaint)
    }
}