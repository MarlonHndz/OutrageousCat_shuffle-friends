package com.outrageouscat.shufflefriends.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Participant(
    val name: String,
    val countryCode: String = "",
    val phoneNumber: String = "",
    val description: String = ""
)
