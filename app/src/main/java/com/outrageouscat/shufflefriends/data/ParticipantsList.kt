package com.outrageouscat.shufflefriends.data

import kotlinx.serialization.Serializable

@Serializable
data class ParticipantsList(
    val participants: List<String> = emptyList()
)

