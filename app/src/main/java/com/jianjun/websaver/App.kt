package com.jianjun.websaver

import android.app.Application
import androidx.multidex.MultiDexApplication
import com.didichuxing.doraemonkit.DoraemonKit
import com.jianjun.websaver.module.db.WebSaverDatabase
import com.ycbjie.webviewlib.X5WebUtils

/**
 * Created by jianjunhuang on 11/14/19.
 */
class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            DoraemonKit.install(this)
            DoraemonKit.setAwaysShowMianIcon(false)
        }
        X5WebUtils.init(this)
        WebSaverDatabase.init(this)
    }
}