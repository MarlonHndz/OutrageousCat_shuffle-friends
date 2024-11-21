package com.outrageouscat.shufflefriends.di

import android.content.Context
import com.outrageouscat.shufflefriends.data.respositories.ParticipantsRepository
import com.outrageouscat.shufflefriends.data.respositories.ResultsRepository
import com.outrageouscat.shufflefriends.data.respositories.SettingsRepository
import com.outrageouscat.shufflefriends.data.util.participantsListDataStore
import com.outrageouscat.shufflefriends.data.util.resultsDataStore
import com.outrageouscat.shufflefriends.data.util.settingsDataStore
import org.koin.dsl.module

val dataModule = module {
    // Repositories -
    // Note: DataStores are being injected in a specific way because koin cannot determine the type
    // of each datastore when using "get()"
    single { ResultsRepository(get<Context>().resultsDataStore) }
    single { ParticipantsRepository(get<Context>().participantsListDataStore) }
    single { SettingsRepository(get<Context>().settingsDataStore) }
}
