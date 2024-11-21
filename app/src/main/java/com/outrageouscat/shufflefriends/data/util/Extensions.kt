package com.outrageouscat.shufflefriends.data.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.outrageouscat.shufflefriends.data.datastore.ParticipantsSerializer
import com.outrageouscat.shufflefriends.data.datastore.ResultsSerializer
import com.outrageouscat.shufflefriends.data.models.ParticipantsList
import com.outrageouscat.shufflefriends.datastore.ResultsProto.ResultsList

val Context.participantsListDataStore: DataStore<ParticipantsList> by dataStore(
    fileName = "participants.json",
    serializer = ParticipantsSerializer
)

val Context.resultsDataStore: DataStore<ResultsList> by dataStore(
    fileName = "results.pb",
    serializer = ResultsSerializer
)
