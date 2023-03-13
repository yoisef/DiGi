package com.digi.movies.data.db

import androidx.room.TypeConverter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {



    @TypeConverter
    fun toListDayJson(genre_ids: List<Int>?): String {
        return Gson().toJson(genre_ids)
    }




    @TypeConverter
    fun getIntList(genre_ids: String?): List<Int>? {
        return Gson().fromJson(
            genre_ids,
            object : TypeToken<List<Int>>() {}.type
        )
    }



}