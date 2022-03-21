package com.example.jjjrey88933.articleapp

import androidx.lifecycle.LiveData

class ArticleRepository constructor(
    private val articleDao: ArticleDao
    ) {

    fun getData(): LiveData<List<Article>> {
        return articleDao.getAll()
    }

    companion object {
        @Volatile private var instance: ArticleRepository? = null

        fun getInstance(articleDao: ArticleDao) =
            instance ?: synchronized(this) {
                instance ?: ArticleRepository(articleDao).also { instance = it }
            }
    }
}