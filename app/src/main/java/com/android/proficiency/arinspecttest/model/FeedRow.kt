package com.android.proficiency.arinspecttest.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class FeedRow(
        @PrimaryKey(autoGenerate = true)
        val id: Int?,
        val title: String? = "",
        val description: String? = "Empty desc",
        val imageHref: String? = ""
)