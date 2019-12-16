package com.jianjun.websaver.module.db.dao

import androidx.room.*
import com.jianjun.websaver.module.db.entity.Pager
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Created by jianjunhuang on 10/28/19.
 */
@Dao
interface PagerDao {

    @Query("SELECT * FROM pager")
    fun queryAllPagers(): Flowable<List<Pager>>

    @Query("SELECT * FROM pager WHERE pager.url in (SELECT url FROM TagsMapper WHERE tag = :tag )")
    fun queryPagersByTag(tag: String): Flowable<List<Pager>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPager(pager: Pager): Completable

    @Delete
    fun deletePager(pager: Pager): Completable
}