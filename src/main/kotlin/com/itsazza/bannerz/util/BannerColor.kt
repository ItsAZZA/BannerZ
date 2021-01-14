package com.itsazza.bannerz.util

import org.bukkit.DyeColor
import org.bukkit.Material
import java.lang.IllegalArgumentException

val Material.bannerColor: DyeColor
    get() {
        return when (this) {
            Material.WHITE_BANNER,
            Material.WHITE_WALL_BANNER -> DyeColor.WHITE
            Material.ORANGE_BANNER,
            Material.ORANGE_WALL_BANNER -> DyeColor.ORANGE
            Material.MAGENTA_BANNER,
            Material.MAGENTA_WALL_BANNER -> DyeColor.MAGENTA
            Material.LIGHT_BLUE_BANNER,
            Material.LIGHT_BLUE_WALL_BANNER -> DyeColor.LIGHT_BLUE
            Material.YELLOW_BANNER,
            Material.YELLOW_WALL_BANNER -> DyeColor.YELLOW
            Material.LIME_BANNER,
            Material.LIME_WALL_BANNER -> DyeColor.LIME
            Material.PINK_BANNER,
            Material.PINK_WALL_BANNER -> DyeColor.PINK
            Material.GRAY_BANNER,
            Material.GRAY_WALL_BANNER -> DyeColor.GRAY
            Material.LIGHT_GRAY_BANNER,
            Material.LIGHT_GRAY_WALL_BANNER -> DyeColor.LIGHT_GRAY
            Material.CYAN_BANNER,
            Material.CYAN_WALL_BANNER -> DyeColor.CYAN
            Material.PURPLE_BANNER,
            Material.PURPLE_WALL_BANNER -> DyeColor.PURPLE
            Material.BLUE_BANNER,
            Material.BLUE_WALL_BANNER -> DyeColor.BLUE
            Material.BROWN_BANNER,
            Material.BROWN_WALL_BANNER -> DyeColor.BROWN
            Material.GREEN_BANNER,
            Material.GREEN_WALL_BANNER -> DyeColor.GREEN
            Material.RED_BANNER,
            Material.RED_WALL_BANNER -> DyeColor.RED
            Material.BLACK_BANNER,
            Material.BLACK_WALL_BANNER -> DyeColor.BLACK
            else -> throw IllegalArgumentException()
        }
    }