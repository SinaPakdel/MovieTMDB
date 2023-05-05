package com.tmdb.movie.di.modules

import com.tmdb.movie.data.remote.service.MovieService
import com.tmdb.movie.di.qualifier.ApiKey
import com.tmdb.movie.di.qualifier.BaseUrl
import com.tmdb.movie.util.api.provideApi
import com.tmdb.movie.util.consts.Services.API_KEY
import com.tmdb.movie.util.consts.Services.API_KEY_VALUE
import com.tmdb.movie.util.consts.Services.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @ApiKey
    fun provideApiKey(): String = API_KEY

    @Provides
    @Singleton
    @BaseUrl
    fun provideBaseUrl(): String = BASE_URL


    @Provides
    @Singleton
    fun provideInterceptor(@ApiKey apiKey: String): Interceptor = Interceptor { chain ->
        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter(API_KEY_VALUE, apiKey)
            .build()
        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        chain.proceed(request)
    }

    @Provides
    @Singleton
    fun provideOkHttpLog(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: Interceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(interceptor)
        .build()


    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        @BaseUrl baseUrl: String
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideMovieService(retrofit: Retrofit) = provideApi<MovieService>(retrofit)
}