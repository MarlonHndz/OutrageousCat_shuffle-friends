package com.outrageouscat.shufflefriends.data.respositories

import androidx.datastore.core.DataStore
import com.outrageouscat.shufflefriends.data.models.Participant
import com.outrageouscat.shufflefriends.datastore.ResultsProto.ParticipantLocal
import com.outrageouscat.shufflefriends.datastore.ResultsProto.ResultsList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ResultsRepository(
    private val resultsDataStore: DataStore<ResultsList>
) {

    val results: Flow<Map<String, Participant>> = resultsDataStore.data
        .map { resultsList ->
            resultsList.resultsMap.map { (key, value) ->
                key to Participant(
                    name = value.name,
                    phoneNumber = value.phoneNumber,
                    description = value.description
                )
            }.toMap()
        }

    suspend fun saveResults(results: Map<Participant, Participant>) {
        resultsDataStore.updateData { currentResults ->
            val updatedResults = results.mapKeys { it.key.name }
                .mapValues { entry ->
                    ParticipantLocal.newBuilder().apply {
                        name = entry.value.name
                        phoneNumber = entry.value.phoneNumber
                        description = entry.value.description
                    }.build()
                }

            currentResults.toBuilder().putAllResults(updatedResults).build()
        }
    }

    suspend fun clearResults() {
        resultsDataStore.updateData { ResultsList.getDefaultInstance() }
    }
}
