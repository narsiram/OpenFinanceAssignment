package com.sumcorp.assignment.data.local.entity

import androidx.room.*
import com.sumcorp.assignment.data.local.typeConverter.TripListTyperConverter
import com.sumcorp.assignment.data.model.TimingInfo

@Entity(tableName = "tabl_trip_info")
data class RouteInfo(

    @PrimaryKey
    val busId: String,

    @ColumnInfo(name = "busRoute")
    @TypeConverters(TripListTyperConverter::class)
    val route: List<TimingInfo>


)