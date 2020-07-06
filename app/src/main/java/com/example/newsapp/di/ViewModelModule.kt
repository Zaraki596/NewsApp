package com.example.newsapp.di

import com.example.newsapp.ui.NewsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        NewsViewModel(get())
    }
}