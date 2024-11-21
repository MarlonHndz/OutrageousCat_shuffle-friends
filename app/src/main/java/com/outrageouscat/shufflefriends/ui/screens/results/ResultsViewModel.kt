package com.outrageouscat.shufflefriends.ui.screens.results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.outrageouscat.shufflefriends.data.models.Participant
import com.outrageouscat.shufflefriends.domain.useCases.ParticipantsUseCase
import com.outrageouscat.shufflefriends.domain.useCases.ResultsUseCase
import com.outrageouscat.shufflefriends.domain.useCases.WhatsappMessageUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ResultsViewModel(
    private val participantsUseCase: ParticipantsUseCase,
    private val resultsUseCase: ResultsUseCase,
    private val whatsappMessageUseCase: WhatsappMessageUseCase
) : ViewModel() {

    val participants: StateFlow<List<Participant>> = participantsUseCase.participants
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    val results: StateFlow<Map<String, Participant>> = resultsUseCase.results
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyMap()
        )

    private val _selectedIndex = MutableStateFlow(0)
    val selectedIndex: StateFlow<Int> = _selectedIndex.asStateFlow()

    val canMoveToPrevious: Flow<Boolean> = _selectedIndex.map { it > 0 }
    val canMoveToNext: Flow<Boolean> =
        combine(_selectedIndex, participants) { index, participants ->
            index < participants.size - 1
        }

    private val _alertMessage = MutableStateFlow<String?>(null)
    val alertMessage: StateFlow<String?> = _alertMessage.asStateFlow()

    fun moveToPreviousParticipant() {
        _selectedIndex.update { index -> maxOf(index - 1, 0) }
    }

    fun moveToNextParticipant() {
        _selectedIndex.update { index -> index + 1 }
    }

    fun sendMessage() {
        viewModelScope.launch {
            val sendingResult = whatsappMessageUseCase.sendMessage(
                selectedIndex = _selectedIndex.value,
                participants = participants.value,
                results = results.value
            )

            sendingResult.onFailure {
                _alertMessage.value = it.message
            }

            sendingResult.onSuccess {
                _alertMessage.value = null
            }
        }
    }

    fun dismissAlert() {
        _alertMessage.value = null
    }
}
