package com.jianjun.websaver.module

import com.jianjun.websaver.base.mvp.IModel
import com.jianjun.websaver.module.db.entity.Pager
import io.reactivex.Completable
import io.reactivex.Flowable

interface IPagerDbModel : IModel {
    fun savePager(pager: Pager): Completable?
    fun queryPagers(): Flowable<List<Pager>>?
    fun savePagers(pager: List<Pager>): Completable?
    fun importDate(pagers: List<Pager>): Completable?
    fun exportDate(): Completable?
}