package com.itsazza.bannerz.util

fun String.capitalizeFirst() = this.lowercase().replaceFirstChar{it.titlecase()}
fun String.beautifyCapitalize() = this.replace("_", " ").capitalizeFirst()
fun String.beautifyCapitalizeWords() = this.split('_').joinToString(" ") { s -> s.lowercase().replaceFirstChar { it.uppercase() } }
fun String.beautify() = this.replace("_", " ").lowercase()