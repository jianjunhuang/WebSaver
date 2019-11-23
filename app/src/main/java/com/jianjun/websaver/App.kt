package com.jianjun.websaver

import android.app.Application
import com.ycbjie.webviewlib.X5WebUtils

/**
 * Created by jianjunhuang on 11/14/19.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        X5WebUtils.init(this)
    }
}