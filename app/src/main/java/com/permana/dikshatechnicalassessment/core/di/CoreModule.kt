package com.permana.dikshatechnicalassessment.core.di

import android.content.Context
import android.content.SharedPreferences
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.permana.dikshatechnicalassessment.core.data.authentication.api.repository.AuthenticationRepository
import com.permana.dikshatechnicalassessment.core.data.authentication.implementation.remote.AuthenticationApi
import com.permana.dikshatechnicalassessment.core.data.authentication.implementation.repository.AuthenticationRepositoryImpl
import com.permana.dikshatechnicalassessment.core.data.product.api.repository.ProductRepository
import com.permana.dikshatechnicalassessment.core.data.product.implementation.database.ProductDatabase
import com.permana.dikshatechnicalassessment.core.data.product.implementation.remote.ProductApi
import com.permana.dikshatechnicalassessment.core.data.product.implementation.repository.ProductRepositoryImpl
import com.permana.dikshatechnicalassessment.core.stash.api.Stash
import com.permana.dikshatechnicalassessment.core.stash.implementation.StashImpl
import com.permana.xsisassessment.core.utils.AppExecutors
import com.permana.xsisassessment.core.utils.HeaderInterceptor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(getChuckerInterceptor(get()))
            .addInterceptor(HeaderInterceptor())
            .build()
    }
    single {
/*        val retrofit = Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(AuthenticationApi::class.java)*/
        Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single { get<Retrofit>().create(AuthenticationApi::class.java) }

    single { get<Retrofit>().create(ProductApi::class.java) }
}

fun getChuckerInterceptor(context: Context): ChuckerInterceptor {
    return ChuckerInterceptor.Builder(context).build()
}

val repositoryModule = module {
    single { ProductDatabase.getInstance(androidContext()) }
    single { get<ProductDatabase>().cartDao() }

    factory { AppExecutors() }
    single<AuthenticationRepository> { AuthenticationRepositoryImpl(get(), get()) }
    single<ProductRepository> { ProductRepositoryImpl(get(), get()) }
}

val stashModule = module {
    single<SharedPreferences> {
        androidContext().getSharedPreferences("diksha_preferences", Context.MODE_PRIVATE)
    }
    single<CoroutineDispatcher> { Dispatchers.IO }
    single<Stash> { StashImpl(get(), get())}
}