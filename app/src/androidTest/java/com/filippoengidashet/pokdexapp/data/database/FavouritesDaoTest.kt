package com.filippoengidashet.pokdexapp.data.database

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavouritesDaoTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var favouritesDao: FavouritesDao

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        appDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        favouritesDao = appDatabase.favouritesDao()
    }

    @Test
    fun favouritesDao() = runTest {

        favouritesDao.insertFavourite(FavoriteEntity("name-1"))
        favouritesDao.insertFavourite(FavoriteEntity("name-2"))
        favouritesDao.insertFavourite(FavoriteEntity("name-3"))

        val favourites = favouritesDao.getAllFavourites().first()
        assertEquals(favourites.get(1).name, "name-2")
    }

    @After
    fun tearDown() {
        appDatabase.close()
        Dispatchers.resetMain()
    }
}
