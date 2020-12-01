package com.sumcorp.assignment.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sumcorp.assignment.data.local.entity.RouteInfo

@Dao
interface TripTimingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTripTimings(routeInfo: RouteInfo)

    @Query("select * from tabl_trip_info WHERE busId = :busId ")
    suspend fun getTripTiming(busId: String): RouteInfo
}