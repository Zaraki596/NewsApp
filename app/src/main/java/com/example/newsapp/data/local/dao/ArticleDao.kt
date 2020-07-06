package com.example.newsapp.data.local.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.model.NewsResponse
import kotlinx.coroutines.flow.Flow

interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: NewsResponse)

    @Query("SELECT * FROM articles where ID = :articleId")
    fun getArticleById(articleId: Int): Flow<Article>

    @Query("SELECT * FROM articles")
    fun getAllArticles(): Flow<NewsResponse>

    @Query("DELETE FROM articles")
    fun deleteAll()
}