package com.example.newsapp.ui.details

import android.os.Bundle
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.api.load
import com.example.newsapp.R
import com.example.newsapp.data.model.Article
import com.example.newsapp.databinding.ActivityNewsDetailsBinding
import com.example.newsapp.ui.NewsViewModel
import com.example.newsapp.ui.base.BaseActivity
import org.koin.android.viewmodel.ext.android.viewModel

class NewsDetailsActivity : BaseActivity<NewsViewModel, ActivityNewsDetailsBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val article: Article = intent.extras?.getParcelable(ARTICLE_KEY)
            ?: throw IllegalArgumentException("Articles must be non-null")

        initArticles(article)


    }

    private fun initArticles(article: Article) {
        binding.ivNewsDetail.load(article.urlToImage) {
            placeholder(CircularProgressDrawable(this@NewsDetailsActivity).apply {
                strokeWidth = 10f
                centerRadius = 50f
                start()
            })
            error(R.drawable.ic_broken_image)
        }
        binding.tvNewsDetailTitle.text = article.title
        binding.tvNewsDetailTimestamp.text = article.publishedAt.subSequence(0, 10)
        binding.tvNewsDetailContent.text = article.description
        binding.tvNewsDetailSource.text = article.source.name

        binding.ivGoBack.setOnClickListener {
            onBackPressed()
        }
    }

    companion object {
        const val ARTICLE_KEY = "article_key"
    }

    override val mViewModel: NewsViewModel by viewModel()

    override fun getViewBinding(): ActivityNewsDetailsBinding =
        ActivityNewsDetailsBinding.inflate(layoutInflater)

}