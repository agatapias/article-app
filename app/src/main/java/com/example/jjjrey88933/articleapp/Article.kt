package com.example.jjjrey88933.articleapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.json.JSONArray

@Parcelize

class Article(
    var id: Int, var title: String, var url: String,
    var imageUrl: String, var newsSite: String, var summary: String,
    var publishedAt: String, var updatedAt: String, var featured: Boolean
) : Parcelable {

    override fun toString(): String {
        return "Article(id=$id, title='$title', url='$url', imageUrl='$imageUrl', newsSite='$newsSite', summary='$summary', publishedAt='$publishedAt', updatedAt='$updatedAt', featured=$featured)"
    }
}