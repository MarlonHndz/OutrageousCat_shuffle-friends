package com.outrageouscat.shufflefriends.data.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.outrageouscat.shufflefriends.data.datastore.ParticipantsSerializer
import com.outrageouscat.shufflefriends.data.datastore.ResultsSerializer
import com.outrageouscat.shufflefriends.data.datastore.SettingsSerializer
import com.outrageouscat.shufflefriends.data.models.ParticipantsList
import com.outrageouscat.shufflefriends.datastore.ResultsProto.ResultsList
import com.outrageouscat.shufflefriends.datastore.SettingsProto.SettingsLocal

val Context.participantsListDataStore: DataStore<ParticipantsList> by dataStore(
    fileName = "participants.json",
    serializer = ParticipantsSerializer
)

val Context.resultsDataStore: DataStore<ResultsList> by dataStore(
    fileName = "results.pb",
    serializer = ResultsSerializer
)

val Context.settingsDataStore: DataStore<SettingsLocal> by dataStore(
    fileName = "settings.pb",
    serializer = SettingsSerializer
)
