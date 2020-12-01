package com.sumcorp.assignment.data.model

import com.google.gson.annotations.SerializedName
import com.sumcorp.assignment.data.local.entity.BusInfo
import com.sumcorp.assignment.data.local.entity.RouteInfo

data class BusTimingResponse(
    @SerializedName("routeInfo")
    val busesList: List<BusInfo>,
    @SerializedName("routeTimings")
    val routeInfo: TripInfo
)