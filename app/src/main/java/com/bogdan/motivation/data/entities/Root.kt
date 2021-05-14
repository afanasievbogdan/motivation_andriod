package com.bogdan.motivation.data.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Root(
    val id: Int,
    val quote: String,
    val author: String
)