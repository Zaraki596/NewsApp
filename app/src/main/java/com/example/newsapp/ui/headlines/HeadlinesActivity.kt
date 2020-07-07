package com.example.newsapp.ui.headlines

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.Observer
import com.example.newsapp.R
import com.example.newsapp.data.model.Article
import com.example.newsapp.databinding.ActivityHeadlinesBinding
import com.example.newsapp.ui.NewsViewModel
import com.example.newsapp.ui.base.BaseActivity
import com.example.newsapp.ui.details.NewsDetailsActivity
import com.example.newsapp.ui.headlines.adapters.HeadlinesListAdapter
import com.example.newsapp.utils.*
import org.koin.android.viewmodel.ext.android.viewModel

class HeadlinesActivity : BaseActivity<NewsViewModel, ActivityHeadlinesBinding>() {

    private val mAdapter: HeadlinesListAdapter = HeadlinesListAdapter(this::onItemClicked)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.newsRecycler.apply {
            adapter = mAdapter
        }

        initArticles()
        handleNetworkChanges()
    }

    override val mViewModel: NewsViewModel by viewModel()

    private fun initArticles() {
        mViewModel.articlesLiveData.observe(this, Observer { state ->
            when (state) {
                is State.Loading -> showLoading(true)
                is State.Success -> {
                    if (state.data.isNotEmpty()) {
                        mAdapter.submitList(state.data.toMutableList())
                        showLoading(false)
                    }
                }
                is State.Error -> {
                    showToast(state.message)
                    showLoading(false)
                }

            }
        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            getArticles()
        }

        // If State isn't `Success` then reload posts.
        if (mViewModel.articlesLiveData.value !is State.Success) {
            getArticles()
        }
    }

    private fun getArticles() {
        mViewModel.getArticles()
    }

    override fun getViewBinding(): ActivityHeadlinesBinding =
        ActivityHeadlinesBinding.inflate(layoutInflater)

    private fun showLoading(isLoading: Boolean) {
        binding.swipeRefreshLayout.isRefreshing = isLoading
    }

    /**
     * Observe network changes i.e. Internet Connectivity
     */
    private fun handleNetworkChanges() {
        NetworkUtils.getNetworkLiveData(applicationContext).observe(this, Observer { isConnected ->
            println("STATE CHANGED = $isConnected")
            if (!isConnected) {
                binding.textViewNetworkStatus.text = getString(R.string.text_no_connectivity)
                binding.networkStatusLayout.apply {
                    show()
                    setBackgroundColor(getColorRes(R.color.colorStatusNotConnected))
                }
            } else {
                if (mViewModel.articlesLiveData.value is State.Error || mAdapter.itemCount == 0) {
                    getArticles()
                }
                binding.textViewNetworkStatus.text = getString(R.string.text_connectivity)
                binding.networkStatusLayout.apply {
                    setBackgroundColor(getColorRes(R.color.colorStatusConnected))

                    animate()
                        .alpha(1f)
                        .setStartDelay(ANIMATION_DURATION)
                        .setDuration(ANIMATION_DURATION)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                hide()
                            }
                        })
                }
            }
        })
    }

    private fun onItemClicked(article: Article, imageView: ImageView, textView: TextView) {
        val intent = Intent(this, NewsDetailsActivity::class.java)
        intent.putExtra(NewsDetailsActivity.ARTICLE_KEY, article)

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            Pair<View, String>(imageView, imageView.transitionName),
            Pair<View, String>(textView, textView.transitionName)
        )
        startActivity(intent, options.toBundle())
    }

    companion object {
        const val ANIMATION_DURATION = 1000.toLong()
    }

}