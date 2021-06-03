package com.bogdan.motivation.data.entities.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// TODO: 15.05.2021 Naming? поменяй название
@JsonClass(generateAdapter = true)
data class ApiQuote(
    // TODO: 15.05.2021 Json(name = "") добавь чтобы не сломалось если поменяешь название
    @Json(name = "id")
    val id: Int,
    @Json(name = "quote")
    val quote: String,
    @Json(name = "author")
    val author: String,
    @Json(name = "theme")
    val theme: String
)