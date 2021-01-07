package com.itsazza.bannerz.util

import org.bukkit.DyeColor
import org.bukkit.Material

val DyeColor.bannerMaterial: Material
    get() {
        return when (this) {
            DyeColor.WHITE -> Material.WHITE_BANNER
            DyeColor.ORANGE -> Material.ORANGE_BANNER
            DyeColor.MAGENTA -> Material.MAGENTA_BANNER
            DyeColor.LIGHT_BLUE -> Material.LIGHT_BLUE_BANNER
            DyeColor.YELLOW -> Material.YELLOW_BANNER
            DyeColor.LIME -> Material.LIME_BANNER
            DyeColor.PINK -> Material.PINK_BANNER
            DyeColor.GRAY -> Material.GRAY_BANNER
            DyeColor.LIGHT_GRAY -> Material.LIGHT_GRAY_BANNER
            DyeColor.CYAN -> Material.CYAN_BANNER
            DyeColor.PURPLE -> Material.PURPLE_BANNER
            DyeColor.BLUE -> Material.BLUE_BANNER
            DyeColor.BROWN -> Material.BROWN_BANNER
            DyeColor.GREEN -> Material.GREEN_BANNER
            DyeColor.RED -> Material.RED_BANNER
            DyeColor.BLACK -> Material.BLACK_BANNER
        }
    }

val DyeColor.dyeMaterial : Material
    get() {
        return when(this) {
            DyeColor.WHITE -> Material.WHITE_DYE
            DyeColor.ORANGE -> Material.ORANGE_DYE
            DyeColor.MAGENTA -> Material.MAGENTA_DYE
            DyeColor.LIGHT_BLUE -> Material.LIGHT_BLUE_DYE
            DyeColor.YELLOW -> Material.YELLOW_DYE
            DyeColor.LIME -> Material.LIME_DYE
            DyeColor.PINK -> Material.PINK_DYE
            DyeColor.GRAY -> Material.GRAY_DYE
            DyeColor.LIGHT_GRAY -> Material.LIGHT_GRAY_DYE
            DyeColor.CYAN -> Material.CYAN_DYE
            DyeColor.PURPLE -> Material.PURPLE_DYE
            DyeColor.BLUE -> Material.BLUE_DYE
            DyeColor.BROWN -> Material.BROWN_DYE
            DyeColor.GREEN -> Material.GREEN_DYE
            DyeColor.RED -> Material.RED_DYE
            DyeColor.BLACK -> Material.BLACK_DYE
        }
    }