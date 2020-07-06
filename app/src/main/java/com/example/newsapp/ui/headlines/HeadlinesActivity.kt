package com.example.newsapp.ui.headlines

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityHeadlinesBinding
import com.example.newsapp.ui.NewsViewModel
import com.example.newsapp.ui.base.BaseActivity
import com.example.newsapp.ui.headlines.adapters.HeadlinesListAdapter
import com.example.newsapp.utils.*
import org.koin.android.viewmodel.ext.android.viewModel

class HeadlinesActivity : BaseActivity<NewsViewModel, ActivityHeadlinesBinding>() {
    private lateinit var mAdapter: HeadlinesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mAdapter = HeadlinesListAdapter()
        binding.newsRecycler.apply {
            adapter = mAdapter
        }

        initArticles()
        handleNetworkChanges()
    }

    private fun initArticles() {
        mViewModel.articlesLiveData.observe(this, Observer { state ->
            when (state) {
                is State.Loading -> showLoading(true)
                is State.Success -> {
                    mAdapter.submitList(state.data)
                    showLoading(false)
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



    override val mViewModel: NewsViewModel by viewModel()

    private fun getArticles() {
        mViewModel.getArticles()
    }

    companion object {
        const val ANIMATION_DURATION = 1000.toLong()
    }

}