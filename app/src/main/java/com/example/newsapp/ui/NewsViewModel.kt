package com.example.newsapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.NewsResponse
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.utils.State
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    private val _articlesLiveData = MutableLiveData<State<NewsResponse>>()
    val articlesLiveData: LiveData<State<NewsResponse>> get() = _articlesLiveData

    fun getArticles() = viewModelScope.launch {
        newsRepository.getAllArticles().collect {
            _articlesLiveData.value = it
        }
    }
}