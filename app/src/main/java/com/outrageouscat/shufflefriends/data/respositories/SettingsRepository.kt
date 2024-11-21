package com.outrageouscat.shufflefriends.data.respositories

import androidx.datastore.core.DataStore
import com.outrageouscat.shufflefriends.datastore.SettingsProto.SettingsLocal
import kotlinx.coroutines.flow.Flow

class SettingsRepository(
    private val settingsDataStore: DataStore<SettingsLocal>
) {
    val settings: Flow<SettingsLocal> = settingsDataStore.data

    suspend fun updateSettings(newSettings: SettingsLocal) {
        settingsDataStore.updateData { currentSettings ->
            currentSettings.toBuilder()
                .setCustomMessage(newSettings.customMessage)
                .setDeliveryDate(newSettings.deliveryDate)
                .build()
        }
    }

    suspend fun updateCustomMessage(newMessage: String) {
        settingsDataStore.updateData { currentSettings ->
            currentSettings.toBuilder()
                .setCustomMessage(newMessage)
                .build()
        }
    }

    suspend fun updateDeliveryDate(newDate: String) {
        settingsDataStore.updateData { currentSettings ->
            currentSettings.toBuilder()
                .setDeliveryDate(newDate)
                .build()
        }
    }
}
