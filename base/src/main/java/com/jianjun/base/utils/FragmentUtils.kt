package com.jianjun.base.utils


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

fun <T : Fragment> FragmentActivity.showFragment(
    fragmentClz: Class<T>,
    layoutId: Int = 0,
    toStack: Boolean = true,
    data: Bundle? = null
): T =
    FragmentUtilExt.showFragment(
        this,
        supportFragmentManager,
        fragmentClz,
        layoutId,
        toStack,
        data
    )

fun <T : Fragment> Fragment.showFragment(
    fragmentClz: Class<T>,
    layoutId: Int = 0,
    toStack: Boolean = true,
    data: Bundle? = null
): T =
    FragmentUtilExt.showFragment(
        requireContext(),
        childFragmentManager,
        fragmentClz,
        layoutId,
        toStack,
        data
    )


object FragmentUtilExt {

    fun <T : Fragment> showFragment(
        context: Context,
        manager: FragmentManager,
        fragmentClz: Class<T>,
        layoutId: Int,
        toStack: Boolean = true,
        data: Bundle? = null
    ): T =
        with(manager) {
            val fragment =
                findFragmentByTag(fragmentClz.name)
                    ?: fragmentFactory.instantiate(context.classLoader, fragmentClz.name)
            beginTransaction().run {
                if (toStack) {
                    addToBackStack(fragmentClz.name)
                }
                fragment.arguments = data
                if (fragment.isAdded) {
                    show(fragment)
                } else {
                    add(layoutId, fragment, fragmentClz.name)
                }
            }.commitAllowingStateLoss()
            return fragment as T
        }
}

fun <T : Fragment> FragmentActivity.getFragment(fragmentClz: Class<T>): T {
    return with(supportFragmentManager) {
        findFragmentByTag(fragmentClz.name)
            ?: fragmentFactory.instantiate(this@getFragment.classLoader, fragmentClz.name)
    } as T
}

fun Fragment.popBackStack() {
    if(this in childFragmentManager.fragments){
        childFragmentManager.popBackStack()
    }
    activity?.supportFragmentManager?.popBackStack()
}

fun Fragment.childPopBackStack() {
    childFragmentManager.popBackStack()
}