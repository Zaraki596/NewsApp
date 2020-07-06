package com.example.newsapp.ui.headlines.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.api.load
import com.example.newsapp.R
import com.example.newsapp.data.model.Article
import com.example.newsapp.databinding.ItemNewsHeadlineBinding

class HeadlinesListAdapter(val clickListener: (article: Article) -> Unit = {}) :
    ListAdapter<Article, HeadlinesListAdapter.HeadlinesViewHolder>(HEADLINE_DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HeadlinesViewHolder(
        ItemNewsHeadlineBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: HeadlinesViewHolder, position: Int): Unit =
        holder.bind(getItem(position))


    inner class HeadlinesViewHolder(
        private val binding: ItemNewsHeadlineBinding
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            if (adapterPosition == RecyclerView.NO_POSITION) return

            val clicked = getItem(adapterPosition)
            clicked.let {
                clickListener.invoke(it)
            }
        }

        fun bind(item: Article) = with(itemView) {
            binding.ivNewsItem.load(item.urlToImage) {
                placeholder(CircularProgressDrawable(context).apply {
                    strokeWidth = 10f
                    centerRadius = 50f
                    start()
                })
                error(R.drawable.ic_broken_image)
            }
            binding.tvNewsTitle.text = item.title
            binding.tvDateStamp.text = item.publishedAt
            binding.tvNewsSource.text = item.source.name

        }
    }

    companion object {
        private val HEADLINE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(
                oldItem: Article,
                newItem: Article
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Article,
                newItem: Article
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}