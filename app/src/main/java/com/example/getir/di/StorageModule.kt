package com.example.getir.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(
            "auth_prefs",
            Context.MODE_PRIVATE
        )
    }

    @Provides
    @Singleton
    fun provideGson(): com.google.gson.Gson {
        return com.google.gson.Gson()
    }

    @Provides
    @Singleton
    fun provideCartPrefs(
        sharedPreferences: SharedPreferences,
        gson: com.google.gson.Gson
    ): com.example.getir.presention.cartView.CartPrefs {
        return com.example.getir.presention.cartView.CartPrefs(sharedPreferences, gson)
    }
}