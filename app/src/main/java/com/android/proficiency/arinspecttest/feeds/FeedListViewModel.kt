package com.android.proficiency.arinspecttest.feeds

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableBoolean
import android.view.View
import com.android.proficiency.arinspecttest.R
import com.android.proficiency.arinspecttest.basemodel.BaseViewModel
import com.android.proficiency.arinspecttest.model.CanadaFacts
import com.android.proficiency.arinspecttest.model.FeedDao
import com.android.proficiency.arinspecttest.model.FeedRow
import com.android.proficiency.arinspecttest.network.FeedAPI
import com.android.proficiency.arinspecttest.utils.SharedPrefHelper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class FeedListViewModel(private val feedDao: FeedDao) : BaseViewModel() {
    @Inject
    lateinit var feedAPI: FeedAPI
    val feedListAdapter: FeedListAdapter = FeedListAdapter()

    //mutable error object
    val errorMessage: MutableLiveData<Int> = MutableLiveData()

    //mutable title update object
    val appBarTitle: MutableLiveData<String> = MutableLiveData()

    //variable to manage visibility of progress bar while fetching facts
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    //variable to handle pull to refreshing property
    var isRefreshing: ObservableBoolean = ObservableBoolean()
    val errorClickListener = View.OnClickListener { loadFacts() }

    private lateinit var subscription: Disposable

    init {
        loadFacts()
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    /**
     * Getting feed data.
     */
    fun loadFacts() {
        subscription = Observable.fromCallable { feedDao.all }
            .concatMap { dbFactsList ->
                if (dbFactsList.isEmpty())
                    feedAPI.getFacts().concatMap { dbFactsList ->
                        feedDao.insertAll(*dbFactsList.rows!!.toTypedArray())
                        Observable.just(dbFactsList)
                    }
                else
                    Observable.just(dbFactsList)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveFeedListStart() }
            .doOnTerminate { onRetrieveFeedListFinish() }
            .subscribe(
                { result -> onRetrieveFeedListSuccess(getResult(result)) },
                { onRetrieveFeedListError() }
            )
    }

    /**
     * set result data.
     */
    private fun getResult(result: Any): List<FeedRow> {
        if (result is CanadaFacts) {
            SharedPrefHelper.storeTitle(if (result.title != null) result.title else "About Canada")
            return result.rows!!
        } else if (result is List<*>) {
            return result as List<FeedRow>
        } else {
            return ArrayList()
        }
    }

    private fun onRetrieveFeedListStart() {
        loadingVisibility.value = View.VISIBLE
        isRefreshing.set(true)
        errorMessage.value = null
    }

    private fun onRetrieveFeedListFinish() {
        loadingVisibility.value = View.GONE
        isRefreshing.set(false)
    }

    private fun onRetrieveFeedListSuccess(factList: List<FeedRow>) {
        appBarTitle.value = SharedPrefHelper.getTitle()
        feedListAdapter.updateFactList(factList)
    }

    private fun onRetrieveFeedListError() {
        errorMessage.value = R.string.api_error
        loadingVisibility.value = View.GONE
        isRefreshing.set(false)
    }
}