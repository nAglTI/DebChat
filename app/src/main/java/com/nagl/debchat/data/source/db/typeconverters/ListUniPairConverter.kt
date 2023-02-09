package com.nagl.debchat.data.source.db.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

//class ListUniPairConverter {
//
//    val gson = Gson()
//    val type: Type = object : TypeToken<List<UniPair?>?>() {}.type
//
//    /**
//     * Converts a listOf [UniPair] to a [String]
//     */
//    @TypeConverter
//    fun fromUniPairList(list: List<UniPair?>?): String {
//        return gson.toJson(list, type)
//    }
//
//    /**
//     * Converts a [String] to a listOf [UniPair]
//     */
//    @TypeConverter
//    fun toUniPairList(json: String?): List<UniPair> {
//        return gson.fromJson(json, type)
//    }
//}