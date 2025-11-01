package com.halimjr11.locaroo.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.halimjr11.locaroo.data.remote.LocalGemApi
import com.halimjr11.locaroo.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    /**
     * Provides the base URL of the API.
     *
     * @return The base URL of the API.
     */
    @Singleton
    @Provides
    @Named(Constant.BASE_KEY)
    fun provideBaseUrl(): String = Constant.BASE_URL

    /**
     * Provides an instance of {@link HttpLoggingInterceptor} with level set to {@link HttpLoggingInterceptor.Level#BODY}.
     *
     * @return An instance of {@link HttpLoggingInterceptor} with level set to {@link HttpLoggingInterceptor.Level#BODY}.
     */
    @Singleton
    @Provides
    fun provideHttpLogging(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        return httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    /**
     * Provides an instance of ChuckerInterceptor which is used to cache responses from the API.
     *
     * @param context The application context.
     * @return An instance of ChuckerInterceptor.
     */
    @Singleton
    @Provides
    fun provideChucker(@ApplicationContext context: Context): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context = context)
            .collector(ChuckerCollector(context))
            .maxContentLength(25000L)
            .alwaysReadResponseBody(false)
            .build()
    }

    /**
     * Provides an instance of {@link OkHttpClient} which is used as the HTTP client.
     * The client is configured with:
     * - The {@link HttpLoggingInterceptor} to log the HTTP requests and responses.
     * - The {@link ChuckerInterceptor} to cache the responses from the API.
     * - A connect timeout of 120 seconds.
     * - A read timeout of 120 seconds.
     *
     * @param logging The HTTP logging interceptor.
     * @param chucker The Chucker interceptor.
     * @return An instance of {@link OkHttpClient}.
     */
    @Singleton
    @Provides
    fun provideOkHttpClient(
        logging: HttpLoggingInterceptor,
        chucker: ChuckerInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(chucker)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    /**
     * Provides an instance of Retrofit with the given base URL and HTTP client.
     * The Retrofit instance is configured with a Gson converter factory.
     *
     * @param baseUrl The base URL of the API.
     * @param client The HTTP client to use for the API requests.
     * @return An instance of Retrofit.
     */
    @Singleton
    @Provides
    @Named(Constant.RETROFIT)
    fun provideRetrofitMoFiz(
        @Named(Constant.BASE_KEY) baseUrl: String,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    /**
     * Provides an instance of {@link LocalGemApi} which is used to communicate with the API.
     *
     * @param retrofit The Retrofit instance to use for creating the API instance.
     * @return An instance of {@link LocalGemApi}.
     */
    @Singleton
    @Provides
    fun provideNewsAPI(
        @Named(Constant.RETROFIT) retrofit: Retrofit
    ): LocalGemApi {
        return retrofit.create(LocalGemApi::class.java)
    }

}