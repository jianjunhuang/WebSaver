package com.jianjun.base.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

/**
 * Created by jianjunhuang on 11/14/19.
 */
open class BaseFragment : Fragment() {

    private var snackbar: Snackbar? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        snackbar = Snackbar.make(view, "", Snackbar.LENGTH_SHORT)
    }

    fun showSnack(msg: String) {
        snackbar?.setText(msg)?.show()
    }

    fun showSnack(view: View, msg: String) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
    }
}