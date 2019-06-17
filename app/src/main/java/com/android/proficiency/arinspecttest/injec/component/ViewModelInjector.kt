package com.android.proficiency.arinspecttest.injec.component

import dagger.Component
import com.android.proficiency.arinspecttest.injec.module.NetworkModule
import com.android.proficiency.arinspecttest.feeds.FeedListViewModel
import com.android.proficiency.arinspecttest.feeds.FeedViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified FeedListViewModel.
     * @param feedListViewModel FeedListViewModel in which to inject the dependencies
     */
    fun inject(feedListViewModel: FeedListViewModel)
    /**
     * Injects required dependencies into the specified FeedViewModel.
     * @param feedViewModel FeedViewModel in which to inject the dependencies
     */
    fun inject(feedViewModel: FeedViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}