package com.example.newsapp.data.model

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
            null,
            "Random Content",
            "Random description",
            dateStamp,
            source,
            "Random title",
            "Google.com",
            null
        )
        val article2 = Article(
            null,
            "Random Content",
            "Random description",
            dateStamp,
            source,
            "Random title",
            "Google.com",
            null
        )
        assertTrue(article==article2)
    }
}