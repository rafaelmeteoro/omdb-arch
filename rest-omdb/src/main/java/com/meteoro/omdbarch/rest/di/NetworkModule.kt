package com.meteoro.omdbarch.rest.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.meteoro.omdbarch.rest.api.ApiInterceptor
import com.meteoro.omdbarch.rest.api.OmdbAPI
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    companion object {
        private const val TIME_OUT = 60L
    }

    @Provides
    @Reusable
    internal fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(httpLoggingInterceptor)
        builder.addInterceptor(ApiInterceptor())
        return builder.build()
    }

    @Provides
    @Reusable
    internal fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(OmdbAPI.API_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Reusable
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        gsonBuilder.registerTypeAdapter(
            Date::class.java,
            JsonDeserializer { json, _, _ -> Date(json.asJsonPrimitive.asLong) }
        )
        return gsonBuilder.create()
    }
}