package com.sumcorp.assignment.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sumcorp.assignment.data.local.entity.BusInfo

@Dao
interface BusListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBus(busInfo: BusInfo)

    @Query("select * from tabl_bus_info")
    suspend fun getBusesList(): List<BusInfo>
}