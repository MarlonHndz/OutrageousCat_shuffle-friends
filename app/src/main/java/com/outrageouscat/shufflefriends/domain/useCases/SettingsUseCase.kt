package com.outrageouscat.shufflefriends.domain.useCases

import com.outrageouscat.shufflefriends.datastore.SettingsProto.SettingsLocal
import com.outrageouscat.shufflefriends.domain.respositories.SettingsRepository
import kotlinx.coroutines.flow.Flow

class SettingsUseCase(
    private val settingsRepository: SettingsRepository
) {
    val settings: Flow<SettingsLocal>
        get() = settingsRepository.settings

    suspend fun updateSettings(newSettings: SettingsLocal) {
        settingsRepository.updateSettings(newSettings)
    }

    suspend fun updateCustomMessage(newMessage: String) {
        settingsRepository.updateCustomMessage(newMessage)
    }

    suspend fun updateDeliveryDate(newDate: String) {
        settingsRepository.updateDeliveryDate(newDate)
    }
}
