package com.jianjun.websaver.presenter

import android.util.Log
import androidx.room.util.StringUtil
import com.jianjun.websaver.base.mvp.BasePresenter
import com.jianjun.websaver.contact.SettingsContact
import com.jianjun.websaver.module.IPagerDbModel
import com.jianjun.websaver.module.PagerDbModel
import com.jianjun.websaver.utils.CSVUtils
import com.jianjun.websaver.utils.flowableToMain
import com.jianjun.websaver.utils.observableToMain
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
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
                return@concatMapCompletable csvUtils?.write(it, Function { pager ->
                    return@Function arrayOf(
                        pager.url.toString(),
                        pager.title.toString(),
                        pager.createDate.toString(),
                        pager.image.toString(),
                        pager.source.toString()
                    ).asList()
                })
            }?.subscribe({
                getView().onDataExport(true, "")
            }, {
                getView().onDataExport(false, it.toString())
                Log.e("", it.toString())
            })?.let { addDisposable(it) }

    }

    public fun importData(csvUtils: CSVUtils?) {

    }
}