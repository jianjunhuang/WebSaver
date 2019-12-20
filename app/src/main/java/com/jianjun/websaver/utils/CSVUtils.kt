package com.jianjun.websaver.utils

import androidx.fragment.app.FragmentActivity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.functions.Function
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*
import kotlin.collections.ArrayList

/**
 * 1. 读取 CSV
 * 2. 生成 CSV
 * 3. 转换成
 *
 * Created by jianjunhuang on 12/13/19.
 */
class CSVUtils(var context: FragmentActivity) {

    public fun <T> write(
        defaultFileName: String,
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
                return@concatMapCompletable write(defaultFileName, it)
            }
    }

    fun write(defaultFileName: String, rows: List<List<String>>): Completable {
        var writer: BufferedWriter? = null
        return RxFile(context).createDoc("$defaultFileName.csv", CSV_TYPE)
            .flatMap {
                writer =
                    BufferedWriter(OutputStreamWriter(context.contentResolver.openOutputStream(it)))
                return@flatMap Observable.fromIterable(rows)
            }
            .compose(observableToMain())
            .doOnNext {
                for ((index, item) in it.withIndex()) {
                    writer?.append(item)
                    if (index != it.size - 1) {
                        writer?.append(",")
                    }
                }
                writer?.append("\n")
            }.doOnComplete {
                writer?.flush()
                writer?.close()
            }.flatMapCompletable { Completable.complete() }

    }

    fun <T> read(mapper: Function<List<String>, T>): Observable<List<T>> {
        var reader: BufferedReader? = null
        return RxFile(context).getDoc(CSV_TYPE)
            .flatMap {
                reader =
                    BufferedReader(InputStreamReader(context.contentResolver.openInputStream(it)))
                return@flatMap Observable.fromIterable(reader?.readLines())
            }.compose(observableToMain()).flatMap {
                return@flatMap Observable.just(fromCSVLinetoArray(it))
            }
            .doOnComplete {
                reader?.close()
            }
            .map(mapper)
            .toList()
            .toObservable()
    }

    fun fromCSVLinetoArray(source: String?): List<String> {
        if (source == null || source.isEmpty()) {
            return ArrayList<String>()
        }
        var currentPosition = 0
        val maxPosition = source.length
        var nextComma = 0
        val rtnArray: ArrayList<String> = ArrayList()
        while (currentPosition < maxPosition) {
            nextComma = nextComma(source, currentPosition)
            rtnArray.add(nextToken(source, currentPosition, nextComma))
            currentPosition = nextComma + 1
            if (currentPosition == maxPosition) {
                rtnArray.add("")
            }
        }
        return rtnArray
    }


    /**
     * 查询下一个逗号的位置。
     *
     * @param source 文字列
     * @param st  检索开始位置
     * @return 下一个逗号的位置。
     */
    private fun nextComma(source: String, st: Int): Int {
        var st = st
        val maxPosition = source.length
        var inquote = false
        while (st < maxPosition) {
            val ch = source[st]
            if (!inquote && ch == ',') {
                break
            } else if ('"' == ch) {
                inquote = !inquote
            }
            st++
        }
        return st
    }

    /**
     * 取得下一个字符串
     */
    private fun nextToken(source: String, st: Int, nextComma: Int): String {
        val strb = StringBuffer()
        var next = st
        while (next < nextComma) {
            val ch = source[next++]
            if (ch == '"') {
                if (st + 1 < next && next < nextComma && source[next] == '"') {
                    strb.append(ch)
                    next++
                }
            } else {
                strb.append(ch)
            }
        }
        return strb.toString()
    }

    companion object {
        const val CSV_TYPE = "*/*"
    }
}