package com.krossmanzs.todolearn.cache

import com.krossmanzs.todolearn.entity.Image
import com.krossmanzs.todolearn.entity.LaunchStatus
import com.krossmanzs.todolearn.entity.RocketLaunch

// it will contain logic common to both platforms
// internal class mean that it is only accessible from within the multiplatform module.
internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    internal fun getAllLaunches(): List<RocketLaunch> {
        return dbQuery.selectAllLaunchesInfo(::mapLaunchSelecting).executeAsList()
    }

    internal fun clearAndCreateLaunches(launches: List<RocketLaunch>) {
        dbQuery.transaction {
            dbQuery.removeAllLaunches()
            launches.forEach { launch ->
                dbQuery.insertLaunch(
                    flightNumber = launch.id,
                    missionName = launch.missionName,
                    launchDateUTC = launch.launchDateUTC,
                    imageSmall = launch.image.small,
                    imageLarge = launch.image.large,
                    statusId = launch.status.id.toLong(),
                    statusName = launch.status.name,
                    statusDescriotion = launch.status.description
                )
            }
        }
    }


    private fun mapLaunchSelecting (
        flightNumber: String,
        missionName: String,
        launchDateUTC: String,
        imageSmall: String,
        imageLarge: String,
        statusId: Long,
        statusName: String,
        statusDescription: String
    ): RocketLaunch {
        return RocketLaunch(
            id = flightNumber,
            missionName = missionName,
            launchDateUTC = launchDateUTC,
            image = Image(
                small = imageSmall,
                large = imageLarge
            ),
            status = LaunchStatus(
                id = statusId.toInt(),
                name = statusName,
                description = statusDescription
            )
        )
    }
}