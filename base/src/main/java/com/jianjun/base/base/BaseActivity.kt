package com.jianjun.base.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.jianjun.base.utils.SnackbarUtils
import java.lang.ref.WeakReference

/**
 * Created by jianjunhuang on 11/14/19.
 */
abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun showSnack(msg: String) {
        SnackbarUtils.showShort(window.decorView, msg)
    }

    fun showSnack(view: View, msg: String) {
        SnackbarUtils.showShort(view, msg)
    }
}