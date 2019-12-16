package com.jianjun.websaver.utils

import androidx.fragment.app.FragmentActivity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.functions.Function
import java.io.*
import kotlin.collections.ArrayList

/**
 * 1. 读取 CSV
 * 2. 生成 CSV
 * 3. 转换成
 *
 * Created by jianjunhuang on 12/13/19.
 */
class CSVUtils(var context: FragmentActivity, private val defaultFileName: String) {

    constructor(context: FragmentActivity) : this(context, "backup")

    public fun <T> write(
        datas: List<T>,
        mapper: Function<T, List<String>>,
        vararg titles: String
    ): Completable {
        val row: ArrayList<List<String>> = ArrayList<List<String>>()
        if (titles.isNotEmpty()) {
            row.add(titles.asList())
        }

        return Observable.fromIterable(datas)
            .compose(observableToMain())
            .map(mapper)
            .toList()
            .toObservable()
            .concatMap {
                row.addAll(it)
                return@concatMap Observable.just(row)
            }.concatMapCompletable {
                return@concatMapCompletable write(it)
            }
    }

    fun write(rows: List<List<String>>): Completable {
        var writer: BufferedWriter? = null
        return RxFile(context, "$defaultFileName.csv").getFileUri()
            .flatMap {
                writer =
                    BufferedWriter(OutputStreamWriter(context.contentResolver.openOutputStream(it)))
                return@flatMap Observable.fromIterable(rows)
            }
            .compose(observableToMain())
            .doOnNext {
                for (item in it) {
                    writer?.append("\"");
                    writer?.append(item)
                    writer?.append("\"")
                    writer?.append(",")
                }
                writer?.append("\n")
            }.doOnComplete {
                writer?.flush()
                writer?.close()
            }.flatMapCompletable { Completable.complete() }

    }

}