package com.jianjun.base.mvvm.livedata

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class DisposableLiveData<T> : MutableLiveData<T>() {

    private val tag = AtomicBoolean(false)
    private val observers = mutableSetOf<Observer<in T>>()
    private val internalObserver = Observer<T> {
        if (tag.compareAndSet(true, false)) {
            observers.forEach { o ->
                o.onChanged(it)
            }
        }
    }

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        observers.add(observer)
        if (!hasObservers()) {
            super.observe(owner, internalObserver)
        }
    }

    override fun removeObserver(observer: Observer<in T>) {
        observers.remove(observer)
        if (observers.isEmpty()) {
            super.removeObserver(internalObserver)
        }
    }

    override fun removeObservers(owner: LifecycleOwner) {
        observers.clear()
        super.removeObservers(owner)
    }

    override fun setValue(value: T) {
        tag.set(true)
        super.setValue(value)
    }

    override fun postValue(value: T) {
        tag.set(true)
        super.postValue(value)
    }


}