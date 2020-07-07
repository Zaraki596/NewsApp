package com.example.newsapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.newsapp.data.local.NewsDatabase
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.model.Source
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArticleDaoTest {

    /*
    * Insert, Read, Delete and article
    * */
    private lateinit var newsDatabase: NewsDatabase
    val source = Source("Cnn")

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @BeforeClass
    fun setup() {
        newsDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NewsDatabase::class.java
        ).build()
    }

    @Test
    @Throws(InterruptedException::class)
    fun insert_the_data_and_Read_the_Data() = runBlocking {
        val source = Source("Cnn")
        val articles = listOf(
            Article(
                "Title1",
                "Author1",
                "Content1",
                "Description1",
                "12-15-2004",
                source,
                "m.com",
                "m.jpg"
            ),
            Article(
                "Title2",
                "Author2",
                "Content2",
                "Description2",
                "11-19-2005",
                source,
                "c.com",
                "l.jpg"
            )
        )
        newsDatabase.getArticleDao().insertArticles(articles)

        val dbArticles = newsDatabase.getArticleDao().getAllArticles()


        assertEquals(dbArticles.toList(), equalTo(articles))

    }

    @After
    fun cleanUp() {
        newsDatabase.close()
    }
}

