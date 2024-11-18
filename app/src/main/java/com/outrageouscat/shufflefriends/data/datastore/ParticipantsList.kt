package com.outrageouscat.shufflefriends.data.datastore

import kotlinx.serialization.Serializable

@Serializable
data class ParticipantsList(
    val participants: List<String> = emptyList()
)

