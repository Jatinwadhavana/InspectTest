package com.android.proficiency.arinspecttest.model.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.android.proficiency.arinspecttest.model.FeedDao
import com.android.proficiency.arinspecttest.model.FeedRow

@Database(entities = [FeedRow::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun factDao(): FeedDao
}