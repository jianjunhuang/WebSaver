package com.jianjun.websaver.module.db.dao

import androidx.room.*
import com.jianjun.websaver.module.db.entity.Tag
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Created by jianjunhuang on 10/29/19.
 */
@Dao
interface TagDao {

    @Query("SELECT * FROM tag")
    fun queryAllTags(): Flowable<List<Tag>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTags(vararg tags: Tag): Completable

    @Delete
    fun deleteTag(tag: Tag): Completable

}