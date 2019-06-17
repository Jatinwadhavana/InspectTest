package com.android.proficiency.arinspecttest.feeds

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.android.proficiency.arinspecttest.R
import com.android.proficiency.arinspecttest.databinding.ItemFeedBinding
import com.android.proficiency.arinspecttest.model.FeedRow
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class FeedListAdapter : RecyclerView.Adapter<FeedListAdapter.ViewHolder>() {
    private lateinit var feedListArray: List<FeedRow>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemFeedBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_feed, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(feedListArray[position])
    }

    override fun getItemCount(): Int {
        return if (::feedListArray.isInitialized) feedListArray.size else 0
    }

    fun updateFactList(factList: List<FeedRow>) {
        this.feedListArray = factList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemFeedBinding) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = FeedViewModel()

        fun bind(rowData: FeedRow) {
            viewModel.bind(rowData)
            binding.viewModel = viewModel
            loadFeedImage(binding.imgFeed, rowData.imageHref)
        }

        private fun loadFeedImage(view: ImageView, imageUrl: String?) {
            Glide.with(view.context)
                .load(imageUrl)
                .apply(RequestOptions().placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background))
                .into(view)
        }
    }
}

