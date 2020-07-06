package com.example.newsapp.data.repository

import com.example.newsapp.data.local.dao.ArticleDao
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.model.NewsResponse
import com.example.newsapp.data.remote.api.NewsApiService
import com.example.newsapp.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class NewsRepository constructor(
    private val articleDao: ArticleDao,
    private val newsApiService: NewsApiService
) {
    fun getAllArticles(): Flow<State<List<Article>>> {
        return object : NetworkBoundRepository<List<Article>, NewsResponse>() {
            override suspend fun saveRemoteData(response: NewsResponse) {
                return articleDao.insertArticles(response.articles)
            }

            override fun fetchFromLocal(): Flow<List<Article>> {
                return articleDao.getAllArticles()
            }

            override suspend fun fetchFromRemote(): Response<NewsResponse> {
                return newsApiService.getTopHeadlines()
            }

        }.asFlow().flowOn(Dispatchers.IO)
    }
}