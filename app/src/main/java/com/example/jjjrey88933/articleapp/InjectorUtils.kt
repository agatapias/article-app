package com.example.jjjrey88933.articleapp

import android.content.Context

object InjectorUtils {

    fun provideArticleViewModelFactory(context: Context): ArticleViewModelFactory {
        val articleRepository = ArticleRepository.getInstance(AppDatabase.getInstance(context).articleDao())
        return ArticleViewModelFactory(articleRepository)
    }
}