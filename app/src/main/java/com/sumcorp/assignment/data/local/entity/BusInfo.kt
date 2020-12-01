package com.sumcorp.assignment.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tabl_bus_info")
data class BusInfo(
    @PrimaryKey
    @SerializedName("id")
    val id: String,

    @ColumnInfo(name = "busName")
    @SerializedName("name")
    val name: String,

    @ColumnInfo(name = "source")
    @SerializedName("source")
    val source: String,

    @ColumnInfo(name = "destination")

    @SerializedName("destination")
    val desination: String,

    @ColumnInfo(name = "tripDuration")
    @SerializedName("tripDuration")
    val tripDuration: String
)