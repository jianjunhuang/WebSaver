package com.jianjun.base.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

/**
 * Created by jianjunhuang on 11/14/19.
 */
abstract class BaseActivity : AppCompatActivity() {
    private var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        snackbar = Snackbar.make(window.decorView, "", Snackbar.LENGTH_SHORT)
    }

    fun showSnack(msg: String) {
        snackbar?.setText(msg)?.show()
    }

    fun showSnack(view: View, msg: String) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
    }
}