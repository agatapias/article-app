package com.example.jjjrey88933.articleapp

import androidx.lifecycle.ViewModel

class ArticleViewModel constructor(
    private val repository: ArticleRepository
) : ViewModel() {

    val getArticles = repository.getData()

}