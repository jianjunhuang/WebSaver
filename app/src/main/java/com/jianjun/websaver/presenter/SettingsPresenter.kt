package com.jianjun.websaver.presenter

import com.jianjun.base.mvp.BasePresenter
import com.jianjun.websaver.contact.SettingsContact
import com.jianjun.websaver.module.IPagerDbModel
import com.jianjun.websaver.module.PagerDbModel
import com.jianjun.websaver.module.db.entity.Pager
import com.jianjun.base.utils.CSVUtils
import com.jianjun.base.utils.completableToMain
import com.jianjun.base.utils.flowableToMain
import com.jianjun.base.utils.observableToMain
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
                        pager.source.toString(),
                        pager.position.toString(),
                        pager.isRead.toString()
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
            val pager = Pager("", "", "", "", 0)
            for ((index, value) in it.withIndex()) {
                when (index) {
                    0 -> pager.url = it[0]
                    1 -> pager.title = it[1]
                    2 -> pager.createDate = it[2].toLong()
                    3 -> pager.image = it[3]
                    4 -> pager.source = it[4]
                    5 -> pager.position = it[5].toLong()
                    6 -> pager.isRead = it[6].toBoolean()
                }
            }
            return@Function pager
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