package com.example.jjjrey88933.articleapp

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {

    @GET("articles")
    fun getArticles() : Call<List<Article>>

    companion object {
        var BASE_URL = "https://api.spaceflightnewsapi.net/v3/"

        fun create() : ApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }

}