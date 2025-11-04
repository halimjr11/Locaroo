package com.halimjr11.locaroo.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.halimjr11.locaroo.utils.Constant.DATASTORE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    /**
     * Provides a [DataStore] instance which is used to store application preferences.
     * The [DataStore] is created using the [PreferenceDataStoreFactory] and the file name specified in [Constant.DATASTORE_NAME].
     * @param context The application context.
     * @return A [DataStore] instance which is used to store application preferences.
     */
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.dataStoreFile(DATASTORE_NAME) }
        )
    }
}
