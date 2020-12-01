package com.sumcorp.assignment.data.model

import com.google.gson.annotations.SerializedName

data class TripInfo(
    @SerializedName("r001")
    val route: List<TimingInfo>,

    @SerializedName("r002")
    val route2: List<TimingInfo>,
    @SerializedName("r003")
    val route3: List<TimingInfo>,
    @SerializedName("r004")
    val route4: List<TimingInfo>,
    @SerializedName("r005")
    val route5: List<TimingInfo>
)