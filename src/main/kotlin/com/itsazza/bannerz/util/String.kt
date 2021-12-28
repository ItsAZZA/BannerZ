package com.itsazza.bannerz.util

fun String.capitalizeFirst() = this.lowercase().replaceFirstChar{it.titlecase()}