package com.outrageouscat.shufflefriends.di

import com.outrageouscat.shufflefriends.domain.useCases.ParticipantsUseCase
import com.outrageouscat.shufflefriends.domain.useCases.ResultsUseCase
import com.outrageouscat.shufflefriends.domain.useCases.SettingsUseCase
import com.outrageouscat.shufflefriends.domain.useCases.WhatsappMessageUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { ParticipantsUseCase(get()) }
    factory { ResultsUseCase(get()) }
    factory { SettingsUseCase(get()) }
    factory { WhatsappMessageUseCase(get(), get()) }
}
