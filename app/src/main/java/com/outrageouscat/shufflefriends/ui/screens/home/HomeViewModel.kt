package com.outrageouscat.shufflefriends.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.outrageouscat.shufflefriends.data.models.Participant
import com.outrageouscat.shufflefriends.domain.useCases.ParticipantsUseCase
import com.outrageouscat.shufflefriends.domain.useCases.ResultsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class HomeViewModel(
    private val participantsUseCase: ParticipantsUseCase,
    private val resultsUseCase: ResultsUseCase
) : ViewModel() {

    private val _participants = MutableStateFlow<List<Participant>>(emptyList())
    val participantsList: StateFlow<List<Participant>> = _participants

    private val _results = MutableStateFlow<Map<Participant, Participant>>(emptyMap())
    // In case we need to control results here in the future
    // val results: StateFlow<Map<Participant, Participant>> = _results

    private val _isResultsEmpty = MutableStateFlow(true)
    val isResultsEmpty: StateFlow<Boolean> = _isResultsEmpty

    init {
        loadParticipants()
    }

    private fun loadParticipants() {
        viewModelScope.launch {
            combine(
                participantsUseCase.participants,
                resultsUseCase.results
            ) { participants, results ->
                _participants.value = participants
                _isResultsEmpty.value = results.isEmpty()
            }.collect()
        }
    }

    fun saveParticipants(participants: List<Participant>) {
        viewModelScope.launch {
            participantsUseCase.saveParticipants(participants)
        }
        clearResults()
    }

    fun shuffleParticipants(participants: List<Participant>) {
        viewModelScope.launch {
            val shuffledResults = participantsUseCase.shuffleParticipants(participants)
            _results.value = shuffledResults
            _isResultsEmpty.value = shuffledResults.isEmpty()
            saveResults(shuffledResults)
        }
    }

    private suspend fun saveResults(results: Map<Participant, Participant>) {
        resultsUseCase.saveResults(results)
    }

    fun clearResults() {
        viewModelScope.launch {
            resultsUseCase.clearResults()
            _results.value = emptyMap()
            _isResultsEmpty.value = true
        }
    }
}
