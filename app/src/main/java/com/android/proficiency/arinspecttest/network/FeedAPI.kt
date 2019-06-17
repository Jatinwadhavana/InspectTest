package com.android.proficiency.arinspecttest.network

import io.reactivex.Observable
import com.android.proficiency.arinspecttest.model.CanadaFacts
import retrofit2.http.GET

interface FeedAPI {
    @GET("/s/2iodh4vg0eortkl/facts")
    fun getFacts(): Observable<CanadaFacts>
}