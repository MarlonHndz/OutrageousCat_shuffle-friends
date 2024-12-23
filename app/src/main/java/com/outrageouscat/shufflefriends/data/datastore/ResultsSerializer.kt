package com.outrageouscat.shufflefriends.data.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.outrageouscat.shufflefriends.datastore.ResultsProto.ResultsList
import java.io.InputStream
import java.io.OutputStream

object ResultsSerializer : Serializer<ResultsList> {
    override val defaultValue: ResultsList
        get() = ResultsList.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): ResultsList {
        try {
            return ResultsList.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Error reading proto", e)
        }
    }

    override suspend fun writeTo(t: ResultsList, output: OutputStream) {
        t.writeTo(output)
    }
}
