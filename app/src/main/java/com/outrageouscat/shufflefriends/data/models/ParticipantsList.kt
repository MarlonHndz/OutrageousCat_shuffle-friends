package com.outrageouscat.shufflefriends.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ParticipantsList(
    val participants: List<Participant> = emptyList()
)
