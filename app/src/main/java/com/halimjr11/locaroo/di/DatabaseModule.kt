package com.halimjr11.locaroo.di

import android.content.Context
import androidx.room.Room
import com.halimjr11.locaroo.data.local.database.FavoriteDao
import com.halimjr11.locaroo.data.local.database.LocalGemDatabase
import com.halimjr11.locaroo.data.local.database.ScheduleDao
import com.halimjr11.locaroo.utils.Constant.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    /**
     * Provides an instance of {@link LocalGemDatabase} which is used to access the local database.
     * The database is created with the name specified in {@link Constant#DATABASE_NAME}.
     *
     * @param context The application context.
     * @return An instance of {@link LocalGemDatabase}.
     */
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): LocalGemDatabase {
        return Room.databaseBuilder(
            context,
            LocalGemDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    /**
     * Provides an instance of {@link ScheduleDao} which is used to access the schedule database.
     *
     * @param database The local database instance.
     * @return An instance of {@link ScheduleDao}.
     */
    @Provides
    @Singleton
    fun provideScheduleDao(database: LocalGemDatabase): ScheduleDao {
        return database.scheduleDao()
    }

    /**
     * Provides an instance of {@link FavoriteDao} which is used to access the favorite database.
     * @param database The local database instance.
     * @return An instance of {@link FavoriteDao}.
     */
    @Provides
    @Singleton
    fun provideFavoriteDao(database: LocalGemDatabase): FavoriteDao {
        return database.favoriteDao()
    }
}
