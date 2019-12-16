package com.jianjun.websaver.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.FragmentActivity
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileWriter


/**
 * Created by jianjunhuang on 12/15/19.
 */
class RxFile(private val activity: FragmentActivity, private val fileName: String) {

    private var callbackFragment: ActivityResultFragment = ActivityResultFragment()

    init {
        activity.supportFragmentManager.beginTransaction()
            .add(callbackFragment, this.javaClass.simpleName).commitNow()
    }

    public fun getFileUri(): Observable<Uri> {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        // you can set file mimetype
        intent.type = "*/*"
        // default file name
        intent.putExtra(Intent.EXTRA_TITLE, fileName)
        val rxActivityResult: RxActivityResult = RxActivityResult(activity)
        return rxActivityResult.startForResult(intent, REQUEST_CODE_FOR_CREATE_FILE)
            .flatMap {
                if (it.resultCode != Activity.RESULT_OK) {
                    return@flatMap Observable.just(null)
                }
                return@flatMap Observable.just(it.data?.data)
            }
    }

    companion object {
        const val REQUEST_CODE_FOR_CREATE_FILE = 777
    }

}