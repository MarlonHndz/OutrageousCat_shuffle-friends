package com.outrageouscat.shufflefriends.data.respositories

import androidx.datastore.core.DataStore
import com.outrageouscat.shufflefriends.data.models.Participant
import com.outrageouscat.shufflefriends.data.models.ParticipantsList
import com.outrageouscat.shufflefriends.domain.respositories.ParticipantsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ParticipantsRepositoryImpl(
    private val participantsDataStore: DataStore<ParticipantsList>,
) : ParticipantsRepository{

    override val participants: Flow<List<Participant>> = participantsDataStore.data
        .map { it.participants }

    override suspend fun saveParticipants(participants: List<Participant>) {
        participantsDataStore.updateData { ParticipantsList(participants) }
    }
}
