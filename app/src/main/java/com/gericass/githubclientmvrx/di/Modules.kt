package com.gericass.githubclientmvrx.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.gericass.githubclientmvrx.data.AuthRepository
import com.gericass.githubclientmvrx.data.AuthRepositoryImpl
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


object Modules {
    val apiModule = module {
        single {
            OkHttpClient()
                .newBuilder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .addNetworkInterceptor(StethoInterceptor())
                .build()
        }

        single {
            Moshi.Builder().build()
        }

        single {
            Retrofit.Builder()
                .client(get())
                .addConverterFactory(MoshiConverterFactory.create(get()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://github.com")
                .build()
        }
    }

    val repositoryModule = module {
        single<AuthRepository> { AuthRepositoryImpl(get()) }
    }
}
