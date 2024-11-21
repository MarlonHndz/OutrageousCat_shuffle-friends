package com.outrageouscat.shufflefriends.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.outrageouscat.shufflefriends.datastore.SettingsProto.SettingsLocal
import com.outrageouscat.shufflefriends.domain.useCases.SettingsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsUseCase: SettingsUseCase
) : ViewModel() {

    private val _settings = MutableStateFlow<SettingsLocal>(SettingsLocal.getDefaultInstance())
    val settings: StateFlow<SettingsLocal> = _settings

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            settingsUseCase.settings.collect {
                _settings.value = it
            }
        }
    }

    fun updateCustomMessage(newMessage: String) {
        viewModelScope.launch {
            settingsUseCase.updateCustomMessage(newMessage)
        }
    }

    fun updateDeliveryDate(newDate: String) {
        viewModelScope.launch {
            settingsUseCase.updateDeliveryDate(newDate)
        }
    }

}
