package com.bogdan.motivation.data.entities

import com.squareup.moshi.JsonClass

// TODO: 15.05.2021 Naming? поменяй название
@JsonClass(generateAdapter = true)
data class Root(
    // TODO: 15.05.2021 Json(name = "") добавь чтобы не сломалось если поменяешь название
    val id: Int,
    val quote: String,
    val author: String
)