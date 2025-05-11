package com.filippoengidashet.pokdexapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
class FavoriteEntity(
    //@PrimaryKey(autoGenerate = true) val id: Int = 0,
    @PrimaryKey val name: String,
)
