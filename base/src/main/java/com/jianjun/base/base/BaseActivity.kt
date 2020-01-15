package com.jianjun.base.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by jianjunhuang on 11/14/19.
 */
open abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}