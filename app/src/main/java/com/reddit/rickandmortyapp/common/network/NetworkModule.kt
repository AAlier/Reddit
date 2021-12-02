package com.reddit.rickandmortyapp.common.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.reddit.rickandmortyapp.BuildConfig
import com.reddit.rickandmortyapp.common.di.InjectionModule
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.scope.Scope
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit

object NetworkModule : InjectionModule {

    private const val DEFAULT_CONNECT_TIMEOUT_SECONDS = 90L
    private const val DEFAULT_READ_TIMEOUT_SECONDS = 90L

    override fun create() = module {
        single {
            GsonBuilder()
                .apply { if (BuildConfig.DEBUG) setPrettyPrinting() }
                .registerTypeAdapter(Calendar::class.java, DateTimeTypeAdapter)
                .create()
        }

        single {
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(get()))
                .client(get())
                .build()
        }
        single {
            createOkHttpClient()
                .apply {
                    if (BuildConfig.DEBUG)
                        addInterceptor(createLoggingInterceptor())
                }
                .build()
        }
    }

    private fun Scope.createLoggingInterceptor(): HttpLoggingInterceptor {
        val gson = get<Gson>()
        val okHttpLogTag = "OkHttp"

        val okHttpLogger = object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                if (!message.startsWith('{') && !message.startsWith('[')) {
                    Timber.tag(okHttpLogTag).d(message)
                    return
                }

                try {
                    val json = JsonParser().parse(message)
                    Timber.tag(okHttpLogTag).d(gson.toJson(json))
                } catch (e: Throwable) {
                    Timber.tag(okHttpLogTag).d(message)
                }
            }
        }
        return HttpLoggingInterceptor(okHttpLogger).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private fun Scope.createOkHttpClient(): OkHttpClient.Builder =
        OkHttpClient.Builder()
            .writeTimeout(DEFAULT_CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(DEFAULT_READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
}
