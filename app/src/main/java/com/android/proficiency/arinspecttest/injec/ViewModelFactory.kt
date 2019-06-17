package com.android.proficiency.arinspecttest.injec

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.persistence.room.Room
import android.support.v7.app.AppCompatActivity
import com.android.proficiency.arinspecttest.model.database.AppDatabase
import com.android.proficiency.arinspecttest.feeds.FeedListViewModel

class ViewModelFactory(private val activity: AppCompatActivity): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedListViewModel::class.java)) {
            val db = Room.databaseBuilder(activity.applicationContext, AppDatabase::class.java, "facts").build()
            @Suppress("UNCHECKED_CAST")
            return FeedListViewModel(db.factDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}