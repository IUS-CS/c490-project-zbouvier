package com.example.wikirando

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Link::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun linkDao(): LinkDao
}