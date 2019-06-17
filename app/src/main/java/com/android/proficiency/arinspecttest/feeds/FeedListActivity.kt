package com.android.proficiency.arinspecttest.feeds

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.android.proficiency.arinspecttest.R
import com.android.proficiency.arinspecttest.databinding.ActivityFeedListBinding
import com.android.proficiency.arinspecttest.injec.ViewModelFactory
import com.android.proficiency.arinspecttest.utils.SharedPrefHelper

/***
 * Feed list screen
 */
class FeedListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFeedListBinding
    private lateinit var viewModel: FeedListViewModel
    private var errorSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPrefHelper.initPreference(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_feed_list)
        binding.feedList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.feedList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        //pull to refresh
        binding.swipeToRefresh.setOnRefreshListener {
            viewModel.loadFacts()
        }
        viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(FeedListViewModel::class.java)
        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })
        binding.viewModel = viewModel

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.try_again, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }
}