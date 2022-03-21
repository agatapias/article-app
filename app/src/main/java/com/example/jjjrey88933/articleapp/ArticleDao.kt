package com.example.jjjrey88933.articleapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(vararg articles: Article)

    @Query("SELECT * FROM article")
    fun getAll(): LiveData<List<Article>>

    @Delete
    suspend fun delete(article: Article)

}