package com.jianjun.base.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.FragmentActivity
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import java.lang.Exception


/**
 * Created by jianjunhuang on 12/15/19.
 */
class RxFile(private val activity: FragmentActivity) {

    private var rxActivityResult: RxActivityResult = RxActivityResult(activity)

    public fun createDoc(fileName: String, docType: String): Observable<Uri> {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = docType
            putExtra(Intent.EXTRA_TITLE, fileName)
        }
        return rxActivityResult.startForResult(intent, REQUEST_CODE_FOR_CREATE_FILE)
            .flatMap {
                if (it.resultCode != Activity.RESULT_OK) {
                    throw Exception("can't find the file")
                }
                return@flatMap Observable.just(it.data?.data)
            }
    }

    companion object {
        const val REQUEST_CODE_FOR_CREATE_FILE = 777
        const val REQUEST_CODE_FOR_GET_FILE = 888
    }

    public fun getDoc(docType: String): Observable<Uri> {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = docType
        }
        return rxActivityResult.startForResult(intent, REQUEST_CODE_FOR_GET_FILE)
            .flatMap {
                if (it.resultCode != Activity.RESULT_OK) {
                    throw Exception("can't find the file")
                }
                return@flatMap Observable.just(it.data?.data)
            }
    }
}