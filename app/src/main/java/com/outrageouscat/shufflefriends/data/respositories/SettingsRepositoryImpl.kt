package com.outrageouscat.shufflefriends.data.respositories

import androidx.datastore.core.DataStore
import com.outrageouscat.shufflefriends.datastore.SettingsProto.SettingsLocal
import com.outrageouscat.shufflefriends.domain.respositories.SettingsRepository
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(
    private val settingsDataStore: DataStore<SettingsLocal>
) : SettingsRepository {

    override val settings: Flow<SettingsLocal> = settingsDataStore.data

    override suspend fun updateSettings(newSettings: SettingsLocal) {
        settingsDataStore.updateData { currentSettings ->
            currentSettings.toBuilder()
                .setCustomMessage(newSettings.customMessage)
                .setDeliveryDate(newSettings.deliveryDate)
                .build()
        }
    }

    override suspend fun updateCustomMessage(newMessage: String) {
        settingsDataStore.updateData { currentSettings ->
            currentSettings.toBuilder()
                .setCustomMessage(newMessage)
                .build()
        }
    }

    override suspend fun updateDeliveryDate(newDate: String) {
        settingsDataStore.updateData { currentSettings ->
            currentSettings.toBuilder()
                .setDeliveryDate(newDate)
                .build()
        }
    }
}
