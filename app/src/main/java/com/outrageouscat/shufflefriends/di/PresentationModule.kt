package com.outrageouscat.shufflefriends.di

import com.outrageouscat.shufflefriends.ui.screens.home.HomeViewModel
import com.outrageouscat.shufflefriends.ui.screens.results.ResultsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { ResultsViewModel(get(), get(), get()) }
}
