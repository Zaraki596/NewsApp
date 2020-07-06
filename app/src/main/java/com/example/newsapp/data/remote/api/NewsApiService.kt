package com.example.newsapp.data.remote.api

import com.example.newsapp.data.model.NewsResponse
import com.example.newsapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country")
        countryCode: String = "us",
        @Query("apikey")
        apiKey: String = Constants.API_KEY
    ): Response<NewsResponse>
}