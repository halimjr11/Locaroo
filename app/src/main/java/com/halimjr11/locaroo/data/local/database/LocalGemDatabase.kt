package com.halimjr11.locaroo.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.halimjr11.locaroo.data.local.model.FavoriteEntity
import com.halimjr11.locaroo.data.local.model.ScheduleEntity

@Database(
    entities = [ScheduleEntity::class, FavoriteEntity::class],
    version = 1,
    exportSchema = false,
    autoMigrations = []
)
abstract class LocalGemDatabase : RoomDatabase() {
    /**
     * Retrieves the DAO object for accessing the schedule database.
     *
     * @return The DAO object for accessing the schedule database.
     */
    abstract fun scheduleDao(): ScheduleDao

    /**
     * Retrieves the DAO object for accessing the favorite database.
     *
     * @return The DAO object for accessing the favorite database.
     */
    abstract fun favoriteDao(): FavoriteDao
}