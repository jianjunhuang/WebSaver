package com.jianjun.websaver.base.mvp

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by jianjunhuang on 11/24/19.
 */
abstract class BasePresenter<V : IView, M : IModel> {
    private lateinit var mView: IView
    private lateinit var mModel: IModel
    private val disposable = CompositeDisposable()
    public fun attach(view: IView) {
        mView = view
        mModel = createModel()
    }

    protected fun addDisposable(disposable: Disposable) {
        this.disposable.add(disposable)
    }

    abstract fun createModel(): M

    protected fun getView(): V {
        return mView as V
    }

    protected fun getModel(): M {
        return mModel as M
    }

    open fun detach() {
        disposable.clear()
    }

}