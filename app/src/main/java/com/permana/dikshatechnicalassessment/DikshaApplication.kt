package com.permana.dikshatechnicalassessment

import android.app.Application
import com.permana.dikshatechnicalassessment.core.di.networkModule
import com.permana.dikshatechnicalassessment.core.di.repositoryModule
import com.permana.dikshatechnicalassessment.core.di.stashModule
import com.permana.dikshatechnicalassessment.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DikshaApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@DikshaApplication)
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    viewModelModule,
                    stashModule,
                )
            )
        }
    }
}