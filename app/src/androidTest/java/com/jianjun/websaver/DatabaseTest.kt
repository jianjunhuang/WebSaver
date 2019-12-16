package com.jianjun.websaver

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jianjun.websaver.module.db.WebSaverDatabase
import com.jianjun.websaver.module.db.dao.PagerDao
import com.jianjun.websaver.module.db.entity.Pager
import io.reactivex.functions.Consumer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by jianjunhuang on 10/29/19.
 */
@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: WebSaverDatabase

    @Before
    fun initDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WebSaverDatabase::class.java
        ).allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertValue() {
        val date = Date()
        val pager =
            Pager("https://www.google.com", "title", "imageUrl", "from", date.time)
        db.pagerDao().insertPager(pager).blockingAwait()

        db.pagerDao().queryAllPagers().test().assertValueCount(1)

    }

    @Test
    fun getValues() {
        db.pagerDao().queryAllPagers().subscribe(Consumer { assert(it.isNotEmpty()) })
    }
}