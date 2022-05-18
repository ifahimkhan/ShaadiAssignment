package com.fahim.shaadi.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProfileModel::class],version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDAO
}