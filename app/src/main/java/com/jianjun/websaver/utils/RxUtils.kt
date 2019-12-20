package com.jianjun.websaver.utils

import io.reactivex.CompletableObserver
import io.reactivex.CompletableTransformer
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by jianjunhuang on 12/16/19.
 */

public fun <T> observableToMain(): ObservableTransformer<T, T> {

    return ObservableTransformer { upstream ->
        upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}

public fun <T> flowableToMain(): FlowableTransformer<T, T> {

    return FlowableTransformer { upstream ->
        upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}

public fun completableToMain(): CompletableTransformer {

    return CompletableTransformer { upstream ->
        upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}