package com.bogdan.motivation.data.entities.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiQuote(
    @Json(name = "id")
    val id: Long,
    @Json(name = "quote")
    val quote: String,
    @Json(name = "author")
    val author: String,
    @Json(name = "theme")
    val theme: String,
    @Json(name = "created_at")
    val created_at: String,
    @Json(name = "updated_at")
    val updated_at: String
)