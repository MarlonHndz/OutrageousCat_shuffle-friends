package com.outrageouscat.shufflefriends.di

import android.content.Context
import com.outrageouscat.shufflefriends.data.respositories.ParticipantsRepositoryImpl
import com.outrageouscat.shufflefriends.data.respositories.ResultsRepositoryImpl
import com.outrageouscat.shufflefriends.data.respositories.SettingsRepositoryImpl
import com.outrageouscat.shufflefriends.data.util.participantsListDataStore
import com.outrageouscat.shufflefriends.data.util.resultsDataStore
import com.outrageouscat.shufflefriends.data.util.settingsDataStore
import com.outrageouscat.shufflefriends.domain.respositories.ParticipantsRepository
import com.outrageouscat.shufflefriends.domain.respositories.ResultsRepository
import com.outrageouscat.shufflefriends.domain.respositories.SettingsRepository
import org.koin.dsl.module

val dataModule = module {
    // Repositories -
    // Note: DataStores are being injected in a specific way because koin cannot determine the type
    // of each datastore when using "get()"
    single<ResultsRepository> { ResultsRepositoryImpl(get<Context>().resultsDataStore) }
    single<ParticipantsRepository> { ParticipantsRepositoryImpl(get<Context>().participantsListDataStore) }
    single<SettingsRepository> { SettingsRepositoryImpl(get<Context>().settingsDataStore) }
}
