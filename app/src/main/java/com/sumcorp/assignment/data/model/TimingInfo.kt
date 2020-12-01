package com.sumcorp.assignment.data.model

import com.google.gson.annotations.SerializedName

data class TimingInfo(
    @SerializedName("totalSeats")
    val totalSeats: Int,
    @SerializedName("avaiable")
    val availableSeats: Int,
    @SerializedName("tripStartTime")
    val startTime: String
)