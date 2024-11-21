package com.outrageouscat.shufflefriends.domain.respositories

import com.outrageouscat.shufflefriends.datastore.SettingsProto.SettingsLocal
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    val settings: Flow<SettingsLocal>

    suspend fun updateSettings(newSettings: SettingsLocal)

    suspend fun updateCustomMessage(newMessage: String)

    suspend fun updateDeliveryDate(newDate: String)
}
