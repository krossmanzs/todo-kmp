package com.krossmanzs.todolearn

import com.krossmanzs.todolearn.cache.IOSDatabaseDriverFactory
import com.krossmanzs.todolearn.entity.RocketLaunch
import com.krossmanzs.todolearn.network.SpaceApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.dsl.module

// will wrap the SpaceSDK class with a lazy Koin injection

class KoinHelper : KoinComponent {
    private val sdk: SpaceSDK by inject<SpaceSDK>()

    suspend fun getLaunches(forceReload: Boolean): List<RocketLaunch> {
        return sdk.getLaunches(forceReload = forceReload)
    }
}

fun initKoin() {
    startKoin {
        modules(module() {
            single<SpaceApi> { SpaceApi() }
            single<SpaceSDK> {
                SpaceSDK(
                    databaseDriverFactory = IOSDatabaseDriverFactory(), api = get()
                )
            }
        })
    }
}
