package com.jianjun.websaver.base

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jianjun.websaver.base.mvp.BasePresenter
import com.jianjun.websaver.base.mvp.IModel
import com.jianjun.websaver.base.mvp.IView

/**
 * Created by jianjunhuang on 11/14/19.
 */
open abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}