package com.outrageouscat.shufflefriends.data.datastore

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.outrageouscat.shufflefriends.datastore.SettingsProto.SettingsLocal
import java.io.InputStream
import java.io.OutputStream

object SettingsSerializer : Serializer<SettingsLocal> {
    override val defaultValue: SettingsLocal
        get() = SettingsLocal.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): SettingsLocal {
        try {
            return SettingsLocal.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Error reading proto", e)
        }
    }

    override suspend fun writeTo(t: SettingsLocal, output: OutputStream) {
        t.writeTo(output)
    }
}

val Context.settingsDataStore: DataStore<SettingsLocal> by dataStore(
    fileName = "settings.pb",
    serializer = SettingsSerializer
)