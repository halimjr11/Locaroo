package com.halimjr11.locaroo.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.halimjr11.locaroo.data.local.model.ScheduleEntity

@Dao
interface ScheduleDao {
    /**
     * Retrieves all schedules from the database.
     * @return A list of all schedules in the database.
     */
    @Query("SELECT * FROM schedule")
    fun getAll(): List<ScheduleEntity>

    /**
     * Inserts a schedule into the database.
     * If a schedule with the same date already exists, it will be ignored.
     * @param schedule The schedule to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(schedule: ScheduleEntity)

    /**
     * Retrieves all schedules from the database that match a given date.
     * @param date The date to search for, in the format "yyyy-MM-dd".
     * @return A list of all schedules in the database that match the given date.
     */
    @Query("SELECT * FROM schedule WHERE date LIKE :date")
    fun getByDate(date: String): List<ScheduleEntity>


    /**
     * Deletes a schedule from the database.
     * @param id The id of the schedule to be deleted.
     */
    @Query("DELETE FROM schedule WHERE id = :id")
    fun delete(id: Long)
}