package com.jianjun.websaver.module.db.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * Created by jianjunhuang on 10/26/19.
 */
@Entity
data class Tag(@PrimaryKey var name: String, @Ignore val size: Int)