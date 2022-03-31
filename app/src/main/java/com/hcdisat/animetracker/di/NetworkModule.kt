package com.hcdisat.animetracker.di

import com.hcdisat.animetracker.network.ApiRepository
import com.hcdisat.animetracker.network.IApiRepository
import com.hcdisat.animetracker.network.KitsuApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    /**
     * Provides [HttpLoggingInterceptor]
     */
    @Provides
    fun providesHttpLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    /**
     * provides [OkHttpClient]
     */
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .build()

    /**
     * provides [KitsuApi]
     */
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): KitsuApi =
        Retrofit.Builder()
            .baseUrl(KitsuApi.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KitsuApi::class.java)

    /**
     * provides [IApiRepository]
     */
    @Provides
    fun providesApiRepository(kitsuApi: KitsuApi): IApiRepository = ApiRepository(kitsuApi)
}

private const val REQUEST_TIMEOUT = 15L