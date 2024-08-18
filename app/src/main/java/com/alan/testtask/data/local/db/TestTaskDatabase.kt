package com.alan.testtask.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alan.testtask.data.local.model.ProfileEntity

@Database(entities = [ProfileEntity::class], version = 1, exportSchema = false)
abstract class TestTaskDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao

    companion object {
        private const val DB_NAME = "TestTaskDb"
        private var INSTANCE: TestTaskDatabase? = null
        private val LOCK = Any()

        fun getInstance(context: Context): TestTaskDatabase {
            INSTANCE?.let { return it }

            synchronized(LOCK) {
                INSTANCE?.let { return it }
                val database =
                    Room.databaseBuilder(context, TestTaskDatabase::class.java, DB_NAME).build()
                INSTANCE = database
                return database
            }
        }
    }
}
