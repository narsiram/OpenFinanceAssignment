package com.sumcorp.assignment.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sumcorp.assignment.data.local.dao.BusListDao
import com.sumcorp.assignment.data.local.dao.TripTimingDao
import com.sumcorp.assignment.data.local.entity.BusInfo
import com.sumcorp.assignment.data.local.entity.RouteInfo
import com.sumcorp.assignment.data.local.typeConverter.TripListTyperConverter


@Database(entities = [BusInfo::class, RouteInfo::class], version = 1)
@TypeConverters(TripListTyperConverter::class)
abstract class BusListDatabase : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "Bus-app"

        @Volatile
        private var INSTANCE: BusListDatabase? = null

        fun getInstance(context: Context): BusListDatabase {
            synchronized(this) {
                var instace = INSTANCE

                if (instace == null) {
                    instace = Room.databaseBuilder(
                        context.applicationContext, BusListDatabase::class.java,
                        DATABASE_NAME
                    ).build()
                }

                INSTANCE = instace
                return instace

            }

        }
    }

    abstract fun getBusListDao(): BusListDao
    abstract fun getTripTimingDao(): TripTimingDao
}