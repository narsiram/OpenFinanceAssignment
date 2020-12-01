package com.sumcorp.assignment.data.local.typeConverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sumcorp.assignment.data.model.TimingInfo
import java.lang.reflect.Type

object TripListTyperConverter {

    @TypeConverter
    @JvmStatic
    fun fromList(value: List<TimingInfo>) = Gson().toJson(value)

    @TypeConverter
    @JvmStatic
    fun toList(value: String): List<TimingInfo> {
        val listType: Type = object : TypeToken<List<TimingInfo>>() {}.type
        return Gson().fromJson(value, listType)
    }
}