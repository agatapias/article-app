package com.example.jjjrey88933.articleapp

import androidx.lifecycle.LiveData

class ArticleRepository constructor(
    private val articleDao: ArticleDao
    ) {

    suspend fun insertData(article: Article) {
        articleDao.insertArticle(article)
    }

    fun getData(): LiveData<List<Article>> {
        return articleDao.getAll()
    }

    suspend fun findById(id: Int): Article {
        return articleDao.findById(id)
    }

    suspend fun deleteData(article: Article) {
        articleDao.delete(article)
    }

    companion object {
        @Volatile private var instance: ArticleRepository? = null

        fun getInstance(articleDao: ArticleDao) =
            instance ?: synchronized(this) {
                instance ?: ArticleRepository(articleDao).also { instance = it }
            }
    }
}