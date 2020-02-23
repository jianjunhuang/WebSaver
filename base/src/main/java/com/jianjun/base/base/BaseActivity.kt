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
    private var snackbar: Snackbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun showSnack(msg: String) {
    }

    fun showSnack(view: View, msg: String) {
        val weakReference = WeakReference<View>(view)
        weakReference.get()?.let {
            //            Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
        }
    }
}