package com.outrageouscat.shufflefriends.domain.respositories

import com.outrageouscat.shufflefriends.data.models.Participant
import kotlinx.coroutines.flow.Flow

interface ParticipantsRepository {

    val participants: Flow<List<Participant>>

    suspend fun saveParticipants(participants: List<Participant>)
}