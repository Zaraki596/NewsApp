package com.example.newsapp.di

import com.example.newsapp.data.local.NewsDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        NewsDatabase.getInstance(androidApplication())
    }
    single {
        get<NewsDatabase>().getArticleDao()
    }
}