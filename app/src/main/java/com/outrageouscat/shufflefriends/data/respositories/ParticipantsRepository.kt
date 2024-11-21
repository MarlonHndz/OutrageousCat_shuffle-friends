package com.outrageouscat.shufflefriends.data.respositories

import androidx.datastore.core.DataStore
import com.outrageouscat.shufflefriends.data.models.Participant
import com.outrageouscat.shufflefriends.data.models.ParticipantsList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ParticipantsRepository(
    private val participantsDataStore: DataStore<ParticipantsList>,
) {

    val participants: Flow<List<Participant>> = participantsDataStore.data
        .map { it.participants }

    suspend fun saveParticipants(participants: List<Participant>) {
        participantsDataStore.updateData { ParticipantsList(participants) }
    }
}
