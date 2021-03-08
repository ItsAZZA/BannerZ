package com.itsazza.bannerz.util

fun Boolean.toInt() = if (this) 1 else 0
fun Int.toBoolean() = this == 1