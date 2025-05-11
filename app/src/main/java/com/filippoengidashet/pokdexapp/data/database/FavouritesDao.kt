package com.filippoengidashet.pokdexapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(item: FavoriteEntity)

    @Query("DELETE FROM favourites WHERE name = :name")
    suspend fun deleteFavourite(name: String)

//    @Query("SELECT * FROM favourites")
//    suspend fun getAllFavourites(): List<FavoriteEntity>

    @Query("SELECT * FROM favourites")
    fun getAllFavourites(): Flow<List<FavoriteEntity>>
}
