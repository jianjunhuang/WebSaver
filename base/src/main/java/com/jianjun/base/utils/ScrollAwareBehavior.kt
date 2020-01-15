package com.jianjun.base.utils

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

class ScrollAwareBehavior(context: Context, attrs: AttributeSet) :
    CoordinatorLayout.Behavior<View>() {
    private var mIsAnimatingOut = false

    private var onScrollListener: OnScrollListener? = null


    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(
            coordinatorLayout, child,
            directTargetChild, target, axes, type
        )
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        super.onNestedScroll(
            coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed,
            dyUnconsumed, type
        )
        if (dyConsumed > 0 && !this.mIsAnimatingOut && child.visibility == View.VISIBLE) {
            // User scrolled down and the FAB is currently visible -> hide the FAB
            animateOut(child)
        } else if (dyConsumed < 0 && child.visibility != View.VISIBLE) {
            // User scrolled up and the FAB is currently not visible -> show the FAB
            animateIn(child)
        }
    }


    // Same animation that FloatingActionButton.Behavior uses to hide the FAB when the AppBarLayout exits
    private fun animateOut(button: View) {
        if (null != onScrollListener) {
            onScrollListener!!.onOut(button)
        }
        ViewCompat.animate(button).translationY((button.height + getMarginBottom(button)).toFloat())
            .setInterpolator(INTERPOLATOR).withLayer()
            .setListener(object : ViewPropertyAnimatorListener {
                override fun onAnimationStart(view: View) {
                    this@ScrollAwareBehavior.mIsAnimatingOut = true
                }

                override fun onAnimationCancel(view: View) {
                    this@ScrollAwareBehavior.mIsAnimatingOut = false
                }

                override fun onAnimationEnd(view: View) {
                    this@ScrollAwareBehavior.mIsAnimatingOut = false
                    view.visibility = View.INVISIBLE
                }
            }).start()


    }

    // Same animation that FloatingActionButton.Behavior uses to show the FAB when the AppBarLayout enters
    private fun animateIn(button: View) {
        if (null != onScrollListener) {
            onScrollListener!!.onIn(button)
        }
        button.visibility = View.VISIBLE
        ViewCompat.animate(button).translationY(0f)
            .setInterpolator(INTERPOLATOR).withLayer().setListener(null)
            .start()

    }

    private fun getMarginBottom(v: View): Int {
        var marginBottom = 0
        val layoutParams = v.layoutParams
        if (layoutParams is ViewGroup.MarginLayoutParams) {
            marginBottom = layoutParams.bottomMargin
        }
        return marginBottom
    }

    fun setOnScrollListener(onScrollListener: OnScrollListener) {
        this.onScrollListener = onScrollListener
    }

    interface OnScrollListener {
        fun onIn(view: View)

        fun onOut(view: View)
    }

    companion object {
        private val INTERPOLATOR = FastOutSlowInInterpolator()
    }
}