package com.outrageouscat.shufflefriends.domain.respositories

import com.outrageouscat.shufflefriends.data.models.Participant
import kotlinx.coroutines.flow.Flow

interface ResultsRepository {

    val results: Flow<Map<String, Participant>>

    suspend fun saveResults(results: Map<Participant, Participant>)

    suspend fun clearResults()
}
