package com.example.newsapp.ui

import com.example.newsapp.data.model.Article
import com.example.newsapp.data.remote.api.model.NewsResponseTest.Companion.source
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.di.networkModule
import com.example.newsapp.di.viewModelModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.Mockito

internal class NewsViewModelTest : KoinTest {

    lateinit var mViewModel: NewsViewModel

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    val repo = declareMock<NewsRepository>()

    @Before
    fun setupViewModel(){
        mViewModel = NewsViewModel(repo)
    }

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(viewModelModule, networkModule)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `check the expected response from the given fake response`() = runBlockingTest {
        startKoin { }
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
            )
        )
        Assert.assertEquals(repo.getAllArticles().toList(), articles)
    }

    @After
    fun closeAll(){
        stopKoin()
    }
}