package com.jianjun.base.widgets

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * Created by jianjunhuang on 1/26/20.
 */
class ScaleLayout : ConstraintLayout {

    private val scaleXAnimate = ObjectAnimator.ofFloat(this, "scaleX", 1f, 0.7f, 1f)
    private val scaleYAnimate = ObjectAnimator.ofFloat(this, "scaleY", 1f, 0.7f, 1f)

    private val animatorSet = AnimatorSet()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, -1)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        setOnClickListener {
            animatorSet.play(scaleXAnimate).with(scaleYAnimate)
            animatorSet.duration = 350
            animatorSet.start()
        }
    }
}