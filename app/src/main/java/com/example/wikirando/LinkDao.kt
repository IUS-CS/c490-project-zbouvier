package com.example.wikirando

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LinkDao {
    @Query("SELECT * FROM link_table")
    fun getAll(): List<Link>

    @Query("SELECT * FROM link_table WHERE link_title = (:userTitle) LIMIT 1")
    fun getByTitle(userTitle: String?): Link

    @Query("SELECT * FROM link_table WHERE link_url = (:userUrl) LIMIT 1")
    fun getByUrl(userUrl: String?): Link

    @Query("SELECT * FROM link_table WHERE link_url IN (:userIds)")
    fun loadAllByURL(userIds: IntArray): List<Link>

    @Insert
    fun insertAll(vararg users: Link)

    @Delete
    fun delete(user: Link)
}