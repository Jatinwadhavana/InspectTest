package com.android.proficiency.arinspecttest.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface FeedDao {
    @get:Query("SELECT * FROM feedrow")
    val all: List<FeedRow>

    @Insert
    fun insertAll(vararg users: FeedRow)
}