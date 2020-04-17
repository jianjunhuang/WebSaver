package com.jianjun.websaver.module.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Created by jianjunhuang on 10/26/19.
 */
@Entity(
    foreignKeys = [ForeignKey(
        entity = Pager::class,
        parentColumns = arrayOf("url"),
        childColumns = arrayOf("url"),
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Tag::class,
        parentColumns = arrayOf("name"),
        childColumns = arrayOf("tag"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class TagsMapper(
    @PrimaryKey(autoGenerate = true) var key: Int,
    @ColumnInfo(index = true)
    var tag: String?,
    @ColumnInfo(index = true)
    var url: String?
)