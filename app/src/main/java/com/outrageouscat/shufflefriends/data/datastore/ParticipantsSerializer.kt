package com.outrageouscat.shufflefriends.data.datastore

import androidx.datastore.core.Serializer
import com.outrageouscat.shufflefriends.data.models.ParticipantsList
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object ParticipantsSerializer : Serializer<ParticipantsList> {
    override val defaultValue: ParticipantsList
        get() = ParticipantsList()

    override suspend fun readFrom(input: InputStream): ParticipantsList {
        return try {
            Json.decodeFromString(
                ParticipantsList.serializer(),
                input.readBytes().decodeToString()
            )
        } catch (e: Exception) {
            defaultValue
        }
    }

    override suspend fun writeTo(
        t: ParticipantsList,
        output: OutputStream
    ) {
        output.write(Json.encodeToString(ParticipantsList.serializer(), t).toByteArray())
    }
}


