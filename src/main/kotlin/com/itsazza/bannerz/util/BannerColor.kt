package com.itsazza.bannerz.util

import org.bukkit.DyeColor
import org.bukkit.Material

val Material.bannerColor: DyeColor
    get() {
        return when (this) {
            Material.WHITE_BANNER -> DyeColor.WHITE
            Material.ORANGE_BANNER -> DyeColor.ORANGE
            Material.MAGENTA_BANNER -> DyeColor.MAGENTA
            Material.LIGHT_BLUE_BANNER -> DyeColor.LIGHT_BLUE
            Material.YELLOW_BANNER -> DyeColor.YELLOW
            Material.LIME_BANNER -> DyeColor.LIME
            Material.PINK_BANNER -> DyeColor.PINK
            Material.GRAY_BANNER -> DyeColor.GRAY
            Material.LIGHT_GRAY_BANNER -> DyeColor.LIGHT_GRAY
            Material.CYAN_BANNER -> DyeColor.CYAN
            Material.PURPLE_BANNER -> DyeColor.PURPLE
            Material.BLUE_BANNER -> DyeColor.BLUE
            Material.BROWN_BANNER -> DyeColor.BROWN
            Material.GREEN_BANNER -> DyeColor.GREEN
            Material.RED_BANNER -> DyeColor.RED
            Material.BLACK_BANNER -> DyeColor.BLACK
            else -> DyeColor.WHITE
        }
    }