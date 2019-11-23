package com.jianjun.websaver.module.db

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.jianjun.websaver.module.db.dao.PagerDao
import com.jianjun.websaver.module.db.dao.TagDao
import com.jianjun.websaver.module.db.entity.Pager
import com.jianjun.websaver.module.db.entity.Tag
import com.jianjun.websaver.module.db.entity.TagsMapper

/**
 * Created by jianjunhuang on 10/26/19.
 */
@Database(entities = [Pager::class, Tag::class, TagsMapper::class], version = 1)
abstract class WebSaverDatabase : RoomDatabase() {

    abstract fun pagerDao(): PagerDao
    abstract fun TagDao(): TagDao

    companion object {
        @Volatile
        private var INSTANCE: WebSaverDatabase? = null

        fun getInstance(context: Context): WebSaverDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                WebSaverDatabase::class.java, "websave.db"
            )
                .build()
    }

    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearAllTables() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}