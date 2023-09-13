package com.example.newsme



data class NewsItem(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)