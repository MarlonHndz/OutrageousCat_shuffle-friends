package com.outrageouscat.shufflefriends.data.respositories

import androidx.datastore.core.DataStore
import com.outrageouscat.shufflefriends.data.models.Participant
import com.outrageouscat.shufflefriends.datastore.ResultsProto.ParticipantLocal
import com.outrageouscat.shufflefriends.datastore.ResultsProto.ResultsList
import com.outrageouscat.shufflefriends.domain.respositories.ResultsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ResultsRepositoryImpl(
    private val resultsDataStore: DataStore<ResultsList>
) : ResultsRepository {

    override val results: Flow<Map<String, Participant>> = resultsDataStore.data
        .map { resultsList ->
            resultsList.resultsMap.map { (key, value) ->
                key to Participant(
                    name = value.name,
                    countryCode = value.countryCode,
                    phoneNumber = value.phoneNumber,
                    description = value.description
                )
            }.toMap()
        }

    override suspend fun saveResults(results: Map<Participant, Participant>) {
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

    override suspend fun clearResults() {
        resultsDataStore.updateData { ResultsList.getDefaultInstance() }
    }
}
