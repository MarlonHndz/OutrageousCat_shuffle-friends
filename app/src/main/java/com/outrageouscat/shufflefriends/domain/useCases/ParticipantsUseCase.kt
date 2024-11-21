package com.outrageouscat.shufflefriends.domain.useCases

import com.outrageouscat.shufflefriends.data.models.Participant
import com.outrageouscat.shufflefriends.data.respositories.ParticipantsRepository
import kotlinx.coroutines.flow.Flow

class ParticipantsUseCase(
    private val participantsRepository: ParticipantsRepository
) {

    val participants: Flow<List<Participant>>
        get() = participantsRepository.participants

    suspend fun saveParticipants(participants: List<Participant>) {
        participantsRepository.saveParticipants(participants)
    }

    fun shuffleParticipants(participants: List<Participant>): Map<Participant, Participant> {
        return shuffleFriends(participants)
    }

    private fun shuffleFriends(participants: List<Participant>): Map<Participant, Participant> {
        if (participants.size < 2) return emptyMap()

        var shuffledParticipants: List<Participant>
        do {
            shuffledParticipants = participants.shuffled()
        } while (participants.indices.any { participants[it] == shuffledParticipants[it] })
        // Repeat shuffle as long as someone is assigned to self

        return participants.zip(shuffledParticipants).toMap()
    }
}
