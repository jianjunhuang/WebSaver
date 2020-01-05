package com.jianjun.websaver.module.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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

        fun init(context: Context) {
            synchronized(this) {
                INSTANCE = buildDatabase(context)
            }
        }

        fun getInstance(): WebSaverDatabase? = INSTANCE

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                WebSaverDatabase::class.java, "websaver.db"
            ).addMigrations(migration1To2)
                .build()

        val migration1To2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                val values: ContentValues = ContentValues()
                values.put("position", 0)
                values.put("isRead", false)
                database.update("Pager", SQLiteDatabase.CONFLICT_FAIL, values, null, null)
            }
        }
    }
}