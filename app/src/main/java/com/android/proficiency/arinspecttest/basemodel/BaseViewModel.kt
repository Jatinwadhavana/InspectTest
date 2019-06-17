package com.android.proficiency.arinspecttest.basemodel

import android.arch.lifecycle.ViewModel
import com.android.proficiency.arinspecttest.feeds.FeedListViewModel
import com.android.proficiency.arinspecttest.feeds.FeedViewModel
import com.android.proficiency.arinspecttest.injec.component.DaggerViewModelInjector
import com.android.proficiency.arinspecttest.injec.component.ViewModelInjector
import com.android.proficiency.arinspecttest.injec.module.NetworkModule

/**
 * Base view model for all ui.
 */
abstract class BaseViewModel:ViewModel(){
    private val injector: ViewModelInjector = DaggerViewModelInjector
            .builder()
            .networkModule(NetworkModule)
            .build()

    init {
        inject()
    }

    /**
     * Injecting dependencies
     */
    private fun inject() {
        when (this) {
            is FeedListViewModel -> injector.inject(this)
            is FeedViewModel -> injector.inject(this)
        }
    }
}