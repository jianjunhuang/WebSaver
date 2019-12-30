package com.jianjun.websaver

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by jianjunhuang on 12/23/19.
 */
class RxJavaTest {

    @Test
    fun test() {
        val subscribe = Observable.just(1)
            .subscribeOn(Schedulers.newThread())
            .map {
                println("thread in ${Thread.currentThread().name}")
                return@map 2
            }
            .map {
                println("thread in ${Thread.currentThread().name}")
                return@map 3
            }

            .subscribe {
                println("thread in ${Thread.currentThread().name}")
            }
    }

}