package com.jianjun.base.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.jianjun.base.utils.SnackbarUtils
import java.lang.ref.WeakReference

/**
 * Created by jianjunhuang on 11/14/19.
 */
open class BaseFragment : Fragment() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun showSnack(msg: String) {
        view?.let {
            SnackbarUtils.showShort(it, msg)
        }
    }

    fun showSnack(view: View, msg: String) {
        SnackbarUtils.showShort(view, msg)
    }
}