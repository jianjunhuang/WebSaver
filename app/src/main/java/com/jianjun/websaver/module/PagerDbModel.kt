package com.jianjun.websaver.module

import com.jianjun.websaver.module.db.WebSaverDatabase
import com.jianjun.websaver.module.db.entity.Pager
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by jianjunhuang on 11/24/19.
 */
class PagerDbModel : IPagerDbModel {
    override fun importDate(pagers: List<Pager>): Completable? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun exportDate(): Completable? {
        return queryPagers()?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.flatMapCompletable {
                //todo create to csv

                return@flatMapCompletable Completable.complete()
            }
    }

    override fun getPagerByUrl(url: String): Flowable<Pager>? {
        return WebSaverDatabase.getInstance()?.pagerDao()?.getPagerByUrl(url)
    }

    override fun queryPagers(): Flowable<List<Pager>>? {
        return WebSaverDatabase.getInstance()?.pagerDao()?.queryAllPagers()
    }

    override fun savePagers(pager: List<Pager>): Completable? {
        return WebSaverDatabase.getInstance()?.pagerDao()?.insertPagers(pager)
    }

    override fun savePager(pager: Pager): Completable? {
        return WebSaverDatabase.getInstance()?.pagerDao()?.insertPager(pager)
    }


}