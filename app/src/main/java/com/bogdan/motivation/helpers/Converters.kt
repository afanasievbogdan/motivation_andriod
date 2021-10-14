package com.bogdan.motivation.helpers

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class Converters {

    private val jsonAdapter: JsonAdapter<List<String>> =
        Moshi.Builder().build().adapter(Types.newParameterizedType(List::class.java, String::class.java))

    @TypeConverter
    fun listStringToJsonString(stringList: List<String>?): String? {
        return jsonAdapter.toJson(stringList)
    }

    @TypeConverter
    fun jsonStringToListString(jsonStr: String?): List<String>? {
        return jsonStr?.let { jsonAdapter.fromJson(jsonStr) }
    }
}