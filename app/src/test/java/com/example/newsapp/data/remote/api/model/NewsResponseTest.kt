package com.example.newsapp.data.remote.api.model

import com.example.newsapp.data.model.Article
import com.example.newsapp.data.model.Source
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class NewsResponseTest{
    companion object{
        val dateStamp = "2020-07-06T04:19:00Z"
        val source = Source("CNN")
    }

    @Test
    fun `Given two articles with given data check whether they are equal`(){
        val article = Article(
            1,
            author = null,
            content =  "Random Content",
            description = "Random description",
            title = "Random title",
            publishedAt = dateStamp,
            source = source,
            url = "Google.com",
            urlToImage = null

        )
        val article2 = Article(
            1,
            author = null,
            content =  "Random Content",
            description = "Random description",
            title = "Random title",
            publishedAt = dateStamp,
            source = source,
            url = "Google.com",
            urlToImage = null
        )
        assertTrue(article==article2)
    }
}