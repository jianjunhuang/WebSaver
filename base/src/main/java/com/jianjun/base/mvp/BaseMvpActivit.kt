package com.jianjun.base.mvp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jianjun.base.base.BaseActivity

/**
 * Created by jianjunhuang on 11/24/19.
 */
open abstract class BaseMvpActivity<P : BasePresenter<*, *>> : BaseActivity(), IView {
    private var presenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
        if (presenter != null) {
            presenter!!.attach(this)
        }
    }

    protected abstract fun createPresenter(): P?

    protected fun getPresenter(): P? {
        return presenter
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.detach()
    }

    override fun onResume() {
        super.onResume()
        presenter?.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter?.onPause()
    }
}