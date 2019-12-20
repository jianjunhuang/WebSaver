package com.jianjun.websaver.presenter

import android.util.Log
import com.jianjun.websaver.base.mvp.BasePresenter
import com.jianjun.websaver.contact.SettingsContact
import com.jianjun.websaver.module.IPagerDbModel
import com.jianjun.websaver.module.PagerDbModel
import com.jianjun.websaver.module.db.entity.Pager
import com.jianjun.websaver.utils.CSVUtils
import com.jianjun.websaver.utils.completableToMain
import com.jianjun.websaver.utils.flowableToMain
import com.jianjun.websaver.utils.observableToMain
import io.reactivex.functions.Function

/**
 * Created by jianjunhuang on 12/16/19.
 */
class SettingsPresenter : BasePresenter<SettingsContact.SettingsView, IPagerDbModel>() {
    override fun createModel(): IPagerDbModel = PagerDbModel()

    public fun exportData(csvUtils: CSVUtils?) {
        getModel().queryPagers()
            ?.compose(flowableToMain())
            ?.concatMapCompletable {
                return@concatMapCompletable csvUtils?.write("WebSaver", it, Function { pager ->
                    return@Function arrayOf(
                        pager.url.toString(),
                        "\"${pager.title.toString()}\"",
                        pager.createDate.toString(),
                        pager.image.toString(),
                        pager.source.toString()
                    ).asList()
                })
            }?.subscribe(object : CompletableObserver() {
                override fun onComplete() {
                    getView().onDataExport(true, "")
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    getView().onDataExport(false, e.toString())
                }

            })

    }

    public fun importData(csvUtils: CSVUtils?) {
        csvUtils?.read(Function<List<String>, Pager> {
            return@Function Pager(
                url = it[0],
                title = it[1],
                createDate = it[2].toLong(),
                image = it[3],
                source = it[4]
            )
        })?.compose(observableToMain())?.flatMapCompletable {
            return@flatMapCompletable getModel().savePagers(it)?.compose(completableToMain())
        }?.subscribe(object : CompletableObserver() {
            override fun onComplete() {
                getView().onDataImport(true, "")
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                getView().onDataImport(false, e.toString())
            }

        })
    }
}