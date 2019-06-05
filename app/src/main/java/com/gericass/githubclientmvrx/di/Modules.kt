package com.gericass.githubclientmvrx.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.gericass.githubclientmvrx.common.navigator.Navigtor
import com.gericass.githubclientmvrx.data.AuthRepository
import com.gericass.githubclientmvrx.data.AuthRepositoryImpl
import com.gericass.githubclientmvrx.data.GitHubRepository
import com.gericass.githubclientmvrx.data.GitHubRepositoryImpl
import com.gericass.githubclientmvrx.navigator.MainNavigator
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
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
                //.addInterceptor(HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                //    Timber.tag("okhttp").d(it)
                //}).apply {
                //    level = HttpLoggingInterceptor.Level.BODY
                //})
                .build()
        }

        single {
            Moshi.Builder().build()
        }

        single(named("auth")) {
            Retrofit.Builder()
                .client(get())
                .addConverterFactory(MoshiConverterFactory.create(get()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://github.com")
                .build()
        }

        single(named("api")) {
            Retrofit.Builder()
                .client(get())
                .addConverterFactory(MoshiConverterFactory.create(get()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://api.github.com")
                .build()
        }
    }

    val repositoryModule = module {
        single<AuthRepository> { AuthRepositoryImpl(androidContext(), get(named("auth"))) }
        single<GitHubRepository> { GitHubRepositoryImpl(androidContext(), get(named("api"))) }
    }

    val navigationModule = module {
        factory<Navigtor.MainNavigator> {
            MainNavigator()
        }
    }
}

