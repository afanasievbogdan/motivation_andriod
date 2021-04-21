package com.bogdan.motivation.entities

import java.util.*
//todo почему var?
data class Root(
    var id: Int,
    var quote: String,
    var author: String,
    var created_at: Date,
    var updated_at: Date
)