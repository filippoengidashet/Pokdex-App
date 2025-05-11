package com.filippoengidashet.pokdexapp.data.di

import android.content.Context
import androidx.room.Room
import com.filippoengidashet.pokdexapp.data.api.PokemonApiService
import com.filippoengidashet.pokdexapp.data.database.AppDatabase
import com.filippoengidashet.pokdexapp.data.database.FavouritesDao
import com.filippoengidashet.pokdexapp.data.repository.FavoritesRepositoryImpl
import com.filippoengidashet.pokdexapp.data.repository.PokemonRepositoryImpl
import com.filippoengidashet.pokdexapp.domain.model.AppConfig
import com.filippoengidashet.pokdexapp.domain.repository.FavoritesRepository
import com.filippoengidashet.pokdexapp.domain.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideRetrofit(config: AppConfig): Retrofit {
        return Retrofit.Builder()
            //.baseUrl("https://pokeapi.co")
            .baseUrl(config.getBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
    }

    @Provides
    fun providePokemonApiService(retrofit: Retrofit): PokemonApiService {
        return retrofit.create(PokemonApiService::class.java)
    }

    @Provides
    fun providePokemonRepository(impl: PokemonRepositoryImpl): PokemonRepository = impl

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = AppDatabase.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavouritesDao(database: AppDatabase): FavouritesDao = database.favouritesDao()

    @Provides
    fun provideFavoritesRepository(impl: FavoritesRepositoryImpl): FavoritesRepository = impl
}
