package com.jianjun.websaver.base.mvp

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.jianjun.websaver.base.BaseFragment

/**
 * Created by jianjunhuang on 11/26/19.
 */
open abstract class BaseMvpFragment<P : BasePresenter<*, *>> : BaseFragment(), IView {
    private var presenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //创建present
        presenter = createPresenter()
        if (presenter != null) {
            presenter!!.attach(this)
        }
    }

    protected abstract fun createPresenter(): P

    protected fun getPresenter(): P? {
        return presenter
    }

    override fun onDestroy() {
        super.onDestroy()
        if (presenter != null) {
            presenter!!.detach()
        }

    }
}