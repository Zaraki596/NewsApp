package com.example.newsapp.ui.headlines.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.api.load
import com.example.newsapp.R
import com.example.newsapp.data.model.Article
import com.example.newsapp.databinding.ItemNewsHeadlineBinding

class HeadlinesListAdapter(private val onItemCLicked: (Article, ImageView, TextView) -> Unit) :
    ListAdapter<Article, HeadlinesListAdapter.HeadlinesViewHolder>(HEADLINE_DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HeadlinesViewHolder(
        ItemNewsHeadlineBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: HeadlinesViewHolder, position: Int): Unit =
        holder.bind(getItem(position), onItemCLicked)


    inner class HeadlinesViewHolder(
        private val binding: ItemNewsHeadlineBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article, clickListener: (Article, ImageView, TextView) -> Unit) =
            with(itemView) {
                article.urlToImage?.let {
                    binding.ivNewsItem.load(article.urlToImage) {
                        placeholder(CircularProgressDrawable(context).apply {
                            strokeWidth = 10f
                            centerRadius = 50f
                            start()
                        })
                            .error(R.drawable.ic_broken_image)
                    }
                }
                binding.tvNewsTitle.text = article.title
                binding.tvDateStamp.text = article.publishedAt.subSequence(0, 10)
                binding.tvNewsSource.text = article.source.name

                binding.root.setOnClickListener {
                    clickListener(article, binding.ivNewsItem, binding.tvNewsTitle)
                }

            }
    }

    companion object {
        private val HEADLINE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(
                oldItem: Article,
                newItem: Article
            ): Boolean {
                return oldItem.title == newItem.title
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