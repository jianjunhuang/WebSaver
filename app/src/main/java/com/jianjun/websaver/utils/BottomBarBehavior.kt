package com.jianjun.websaver.utils

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout

/**
 * Created by jianjunhuang on 11/14/19.
 */
class BottomBarBehavior(context: Context?, attrs: AttributeSet?) :
    CoordinatorLayout.Behavior<View>(context, attrs) {

    var defaultDependencyTop = -1

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return true
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        if (defaultDependencyTop == -1) {
            defaultDependencyTop = dependency.top
        }
        child.translationY = (defaultDependencyTop - dependency.top).toFloat()
        return true;
    }

}