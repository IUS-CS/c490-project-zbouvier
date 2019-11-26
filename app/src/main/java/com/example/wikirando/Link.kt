package com.example.wikirando

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "link_table")
data class Link(
    @PrimaryKey(autoGenerate = true)
    var foodId: Int = 0,
    @ColumnInfo(name = "link_title") var linkTitle: String?,
    @ColumnInfo(name = "link_url") val linkUrl: String?
)