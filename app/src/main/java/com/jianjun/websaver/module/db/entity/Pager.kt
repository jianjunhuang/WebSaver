package com.jianjun.websaver.module.db.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlin.collections.ArrayList

/**
 * Created by jianjunhuang on 10/26/19.
 */
@Entity
data class Pager(
    @PrimaryKey var url: String,
    var title: String?,
    var image: String?,
    var source: String?,
    var createDate: Long,
    var isRead: Boolean = false,
    var position: Long = 0
)

