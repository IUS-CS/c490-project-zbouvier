package com.example.wikirando

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Link::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun linkDao(): LinkDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getAppDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "link-database"
                )
                    // allow queries on the main thread.
                    // Don't do this on a real app! See PersistenceBasicSample for an example.
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE as AppDatabase

        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}