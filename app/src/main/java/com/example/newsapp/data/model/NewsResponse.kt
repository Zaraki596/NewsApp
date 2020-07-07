package com.example.newsapp.data.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsResponse(
    val articles: List<Article>
) : Parcelable

@Entity(
    tableName = "articles"
)
@Parcelize
data class Article(
    @PrimaryKey
    val title: String,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String,
    val source: Source,
    val url: String?,
    val urlToImage: String?
) : Parcelable

@Parcelize
data class Source(
    val name: String?
) : Parcelable