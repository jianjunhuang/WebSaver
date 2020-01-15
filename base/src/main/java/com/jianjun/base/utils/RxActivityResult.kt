package com.jianjun.base.utils

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

/**
 * Created by jianjunhuang on 12/16/19.
 */
class RxActivityResult(private val activity: FragmentActivity) {

    private lateinit var resultFragment: ActivityResultFragment

    companion object {
        const val TAG = "RxResult"
    }

    init {
        resultFragment = getResultFragment(activity)
    }

    private fun getResultFragment(activity: FragmentActivity): ActivityResultFragment {
        var fragment: ActivityResultFragment? =
            activity.supportFragmentManager.findFragmentByTag(TAG) as ActivityResultFragment?
        if (fragment == null) {
            fragment = ActivityResultFragment()
            val manager: FragmentManager = activity.supportFragmentManager
            manager.beginTransaction()
                .add(fragment, TAG)
                .commitAllowingStateLoss()
            manager.executePendingTransactions()
        }
        return fragment
    }

    public fun startForResult(data: Intent, requestCode: Int): Observable<ResultMessage> {
        return resultFragment.startForResult(data, requestCode)
    }

}

data class ResultMessage(val resultCode: Int, val requestCode: Int, val data: Intent?)

class ActivityResultFragment : Fragment() {

    private var subject: PublishSubject<ResultMessage>? = null

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    public fun startForResult(data: Intent, requestCode: Int): Observable<ResultMessage> {
        subject = PublishSubject.create()
        return subject!!.doOnSubscribe {
            disposable = it
            startActivityForResult(data, requestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val message: ResultMessage = ResultMessage(resultCode, requestCode, data)
        subject?.onNext(message)
        subject?.onComplete()
    }

    override fun onDetach() {
        super.onDetach()
        disposable?.dispose()
    }
}