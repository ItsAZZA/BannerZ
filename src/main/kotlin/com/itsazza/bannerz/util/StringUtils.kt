package com.itsazza.bannerz.util

object StringUtils {
    fun beautifyCapitalized(text: String): String {
        return text.replace("_", " ").capitalizeFirst()
    }

    fun beautify(text: String): String {
        return text.replace("_", " ")
    }
}