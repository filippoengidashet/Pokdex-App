package com.filippoengidashet.pokdexapp.common

inline fun <T> T?.orElse(alt: () -> T): T = this ?: alt()
