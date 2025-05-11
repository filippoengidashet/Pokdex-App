package com.filippoengidashet.pokdexapp.common

import com.filippoengidashet.pokdexapp.BuildConfig
import kotlin.random.Random

object Utils {

    const val TAG = "PokemonApp_LOG"

    fun log(message: String) {
        if (BuildConfig.DEBUG) {
            android.util.Log.d(TAG, message)
        }
    }

    fun getRandomBoolean(): Boolean {
        return Random.nextBoolean()
    }
}
