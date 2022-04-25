package com.example.jjjrey88933.articleapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(vararg articles: Article)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Article)

    @Query("SELECT * FROM article")
    fun getAll(): LiveData<List<Article>>

    @Query("SELECT * FROM article WHERE id = :id LIMIT 1")
    suspend fun findById(id: Int): Article

    @Delete
    suspend fun delete(article: Article)

}