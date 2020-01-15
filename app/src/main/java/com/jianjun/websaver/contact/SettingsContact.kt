package com.jianjun.websaver.contact

import com.jianjun.base.mvp.IView

/**
 * Created by jianjunhuang on 12/16/19.
 */
interface SettingsContact {
    interface SettingsView : IView {
        fun onDataImport(isImported: Boolean, error: String)
        fun onDataExport(isExported: Boolean, error: String)
    }
}