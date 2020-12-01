package com.sumcorp.assignment.data.dataSource

import android.content.Context
import com.google.gson.Gson
import com.sumcorp.assignment.data.local.dao.BusListDao
import com.sumcorp.assignment.data.local.dao.TripTimingDao
import com.sumcorp.assignment.data.local.entity.BusInfo
import com.sumcorp.assignment.data.local.entity.RouteInfo
import com.sumcorp.assignment.data.model.BusTimingResponse
import com.sumcorp.assignment.data.model.EnumrationID
import com.sumcorp.assignment.data.model.TimingInfo
import com.sumcorp.assignment.data.model.TripInfo
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

class LocalDataSource(
    val context: Context,
    val busListDao: BusListDao,
    val tripTimingDao: TripTimingDao
) {

    suspend fun getTripTimings(busId: String) = tripTimingDao.getTripTiming(busId).route

    suspend fun getBusesFromDB(): List<BusInfo> {
        val busTimingResponse = getJsonFileFromLocal()
        insertBusInfoLocal(busTimingResponse.busesList, busTimingResponse.routeInfo)
        return busListDao.getBusesList()
    }

    private suspend fun insertBusInfoLocal(busesList: List<BusInfo>, routeInfo: TripInfo) {
        for (bus in busesList) {
            busListDao.insertBus(bus)
            insertBusTimings(bus.id, routeInfo)
        }
    }

    private suspend fun insertBusTimings(busId: String, routeInfo: TripInfo) {

        tripTimingDao.insertTripTimings(RouteInfo(busId, getBusTimings(busId, routeInfo)))
    }

    private fun getBusTimings(id: String, routeInfo: TripInfo): List<TimingInfo> {
        var timingList: List<TimingInfo>? = null
        when (id) {
            EnumrationID.r001.name -> timingList = routeInfo.route
            EnumrationID.r002.name -> timingList = routeInfo.route2
            EnumrationID.r003.name -> timingList = routeInfo.route3
            EnumrationID.r004.name -> timingList = routeInfo.route4
            EnumrationID.r005.name -> timingList = routeInfo.route5
        }

        return timingList!!

    }

    private suspend fun getAssetJsonData(context: Context): String? {
        val json: String
        val file: InputStream
        json = try {
            file = context.assets.open("sample.json")
            file.bufferedReader().use { it.readText() }
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    private suspend fun getJsonFileFromLocal(): BusTimingResponse {
        var busTimingResponse: BusTimingResponse? = null
        try {
            if (getAssetJsonData(context) != null) {
                val jsonObject = JSONObject(getAssetJsonData(context)!!)
                busTimingResponse =
                    Gson().fromJson(jsonObject.toString(), BusTimingResponse::class.java)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return busTimingResponse!!
    }

}