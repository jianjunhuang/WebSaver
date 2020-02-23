package com.jianjun.base.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import java.lang.ref.WeakReference

/**
 * Created by jianjunhuang on 11/14/19.
 */
open class BaseFragment : Fragment() {

    private var snackbar: Snackbar? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weakReference = WeakReference<View>(view)
        weakReference.get()?.let {
//            snackbar = Snackbar.make(it, "", Snackbar.LENGTH_SHORT)
        }
    }

    fun showSnack(msg: String) {
//        snackbar?.setText(msg)?.show()
    }

    fun showSnack(view: View, msg: String) {
        val weakReference = WeakReference<View>(view)
        weakReference.get()?.let {
//            Snackbar.make(it, msg, Snackbar.LENGTH_SHORT).show()
        }
    }
}