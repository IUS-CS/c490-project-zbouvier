package com.example.wikirando

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "link_table")
data class Link(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "link_title") val linkTitle: String?,
    @ColumnInfo(name = "link_url") val linkUrl: String?
)