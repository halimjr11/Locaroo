package com.halimjr11.locaroo.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.halimjr11.locaroo.data.local.model.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favorite")
    suspend fun getAll(): Flow<List<FavoriteEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite WHERE id = :id)")
    suspend fun isFavorite(id: Long): Boolean

    @Query("DELETE FROM favorite WHERE id = :id")
    suspend fun delete(id: Long)

}