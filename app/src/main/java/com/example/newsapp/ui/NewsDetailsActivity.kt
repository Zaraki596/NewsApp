package com.example.newsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.R

class NewsDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)
    }

    companion object {
        const val ARTICLEKEY = "article_key"
    }
}