package com.example.jjjrey88933.articleapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticleViewModel constructor(
    private val repository: ArticleRepository
) : ViewModel() {

    val getArticles = repository.getData()

    fun insertArticle(article: Article) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertData(article)
        }
    }

    suspend fun findById(id: Int): Article {
        return repository.findById(id)
    }

    fun deleteArticle(article: Article) {

        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteData(article)
        }
    }

}