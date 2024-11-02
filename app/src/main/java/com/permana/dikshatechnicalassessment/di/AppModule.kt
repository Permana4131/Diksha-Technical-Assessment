package com.permana.dikshatechnicalassessment.di

import com.permana.dikshatechnicalassessment.feature.home.HomeViewModel
import com.permana.dikshatechnicalassessment.feature.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { HomeViewModel(get()) }
}