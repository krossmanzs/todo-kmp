package com.krossmanzs.todolearn

import com.krossmanzs.todolearn.cache.Database
import com.krossmanzs.todolearn.cache.DatabaseDriverFactory
import com.krossmanzs.todolearn.entity.RocketLaunch
import com.krossmanzs.todolearn.network.SpaceApi

class SpaceSDK(databaseDriverFactory: DatabaseDriverFactory, val api: SpaceApi) {
    private val database = Database(databaseDriverFactory)

    @Throws(Exception::class)
    suspend fun getLaunches(forceReload: Boolean): List<RocketLaunch> {
        val cachedLaunches = database.getAllLaunches()
        return if (cachedLaunches.isNotEmpty() && !forceReload) {
            cachedLaunches
        } else {
            api.getAllLaunches().also {
                database.clearAndCreateLaunches(it)
            }
        }
    }
}