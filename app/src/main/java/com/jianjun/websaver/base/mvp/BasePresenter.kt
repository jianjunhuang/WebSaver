package com.jianjun.websaver.base.mvp

import com.jianjun.websaver.utils.HLog
import io.reactivex.CompletableObserver
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

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


    abstract inner class Observer<T> : io.reactivex.Observer<T> {
        override fun onComplete() {
        }

        override fun onSubscribe(d: Disposable) {
            addDisposable(d)
        }

        override fun onError(e: Throwable) {
            HLog.e(e.toString(), tr = e)
        }

    }

    abstract inner class CompletableObserver : io.reactivex.CompletableObserver {

        override fun onSubscribe(d: Disposable) {
            addDisposable(d)
        }

        override fun onError(e: Throwable) {
            HLog.e(e.toString(), tr = e)
        }

    }

    inner class FlowableObserver<T> : Subscriber<T> {
        override fun onComplete() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onSubscribe(s: Subscription?) {
        }

        override fun onNext(t: T) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onError(t: Throwable?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
}