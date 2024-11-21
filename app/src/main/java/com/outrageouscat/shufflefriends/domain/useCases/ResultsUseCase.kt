package com.outrageouscat.shufflefriends.domain.useCases

import com.outrageouscat.shufflefriends.data.models.Participant
import com.outrageouscat.shufflefriends.domain.respositories.ResultsRepository
import kotlinx.coroutines.flow.Flow

class ResultsUseCase(
    private val resultsRepository: ResultsRepository
) {

    val results: Flow<Map<String, Participant>>
        get() = resultsRepository.results

    suspend fun saveResults(results: Map<Participant, Participant>) {
        resultsRepository.saveResults(results)
    }

    suspend fun clearResults() {
        resultsRepository.clearResults()
    }
}
