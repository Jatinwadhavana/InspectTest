package com.android.proficiency.arinspecttest.feeds

import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.android.proficiency.arinspecttest.basemodel.BaseViewModel
import com.android.proficiency.arinspecttest.model.FeedRow


class FeedViewModel: BaseViewModel() {
    private val factTitle = MutableLiveData<String>()
    private val factBody = MutableLiveData<String>()
    private val visibility = MutableLiveData<Int>()

    fun bind(fact: FeedRow){
        factTitle.value = fact.title
        factBody.value = fact.description
        visibility.value = if(fact.title == null || fact.title.isEmpty()) View.GONE else View.VISIBLE
    }

    fun getFactTitle():MutableLiveData<String>{
        return factTitle
    }


    fun getFactBody():MutableLiveData<String>{
        return factBody
    }

    fun getVisibility():MutableLiveData<Int>{
        return visibility
    }
}