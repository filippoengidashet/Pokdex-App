package com.filippoengidashet.pokdexapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.filippoengidashet.pokdexapp.data.database.AppDatabase.Companion.DB_VERSION

@Database(entities = [FavoriteEntity::class], version = DB_VERSION, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favouritesDao(): FavouritesDao

    companion object {
        const val DB_NAME = "pokemon_database"
        const val DB_VERSION = 1
    }
}
