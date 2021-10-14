package com.bogdan.motivation.data.entities.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiQuote(
    @Json(name = "id")
    val id: Long,
    @Json(name = "author")
    val author: String?,
    @Json(name = "quote")
    val content: String?,
    @Json(name = "theme")
    val theme: String?,
    @Json(name = "comments")
    val comments: MutableList<Any>?,
    @Json(name = "created_at")
    val createdAt: String?,
    @Json(name = "updated_at")
    val updatedAt: String?
)