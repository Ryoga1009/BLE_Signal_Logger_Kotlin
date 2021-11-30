package com.ryoga.blelogger.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BeaconInfo(
    @SerialName("beaconName")
    val beaconName: String,
    @SerialName("uuid")
    val uuid: String,
    @SerialName("major")
    val major: String,
    @SerialName("minor")
    val minor: String
)
