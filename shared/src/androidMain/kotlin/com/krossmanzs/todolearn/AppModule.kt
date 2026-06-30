package com.krossmanzs.todolearn

import com.krossmanzs.todolearn.cache.AndroidDatabaseDriverFactory
import com.krossmanzs.todolearn.network.SpaceApi
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel

val appModule = module {
    single<SpaceApi> { SpaceApi() }
    single<SpaceSDK> {
        SpaceSDK(
            databaseDriverFactory = AndroidDatabaseDriverFactory(androidContext()),
            api = get()
        )
    }

    viewModel { RocketLaunchViewModel(sdk = get()) }
}