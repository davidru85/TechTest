package com.ruizurraca.techtest.domain.model


import com.google.gson.annotations.SerializedName

data class Station(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("x")
    val coordinateX: Double? = null,
    @SerializedName("y")
    val coordinateY: Double? = null,
    @SerializedName("spacesAvailable")
    val spacesAvailable: Int? = null,
    @SerializedName("bikesAvailable")
    val bikesAvailable: Int? = null,
    @SerializedName("station")
    val station: Boolean? = null,
    @SerializedName("allowDropoff")
    val allowDropoff: Boolean? = null,
    @SerializedName("realTimeData")
    val realTimeData: Boolean? = null,
    @SerializedName("companyZoneId")
    val companyZoneId: Int? = null,
    @SerializedName("availableResources")
    val availableResources: Int? = null
)


