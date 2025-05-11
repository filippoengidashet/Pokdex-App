package com.filippoengidashet.pokdexapp.ui.di

import com.filippoengidashet.pokdexapp.domain.model.AppConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppConfig(): AppConfig {
        return object : AppConfig {

            override fun getBaseUrl(): String {
                return "https://pokeapi.co" //or BuildConfig.BASE_URL for other variants (prod, staging, dev, CI, etc.)
            }
        }
    }
}
