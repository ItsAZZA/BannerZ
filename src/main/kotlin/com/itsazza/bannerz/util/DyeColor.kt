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

val DyeColor.woolMaterial : Material
    get() {
        return when(this) {
            DyeColor.WHITE -> Material.WHITE_WOOL
            DyeColor.ORANGE -> Material.ORANGE_WOOL
            DyeColor.MAGENTA -> Material.MAGENTA_WOOL
            DyeColor.LIGHT_BLUE -> Material.LIGHT_BLUE_WOOL
            DyeColor.YELLOW -> Material.YELLOW_WOOL
            DyeColor.LIME -> Material.LIME_WOOL
            DyeColor.PINK -> Material.PINK_WOOL
            DyeColor.GRAY -> Material.GRAY_WOOL
            DyeColor.LIGHT_GRAY -> Material.LIGHT_GRAY_WOOL
            DyeColor.CYAN -> Material.CYAN_WOOL
            DyeColor.PURPLE -> Material.PURPLE_WOOL
            DyeColor.BLUE -> Material.BLUE_WOOL
            DyeColor.BROWN -> Material.BROWN_WOOL
            DyeColor.GREEN -> Material.GREEN_WOOL
            DyeColor.RED -> Material.RED_WOOL
            DyeColor.BLACK -> Material.BLACK_WOOL
        }
    }

val DyeColor.dyeMaterial: Material
    get() {
        return when (this) {
            DyeColor.WHITE -> Material.values().firstOrNull { it.name == "WHITE_DYE" }
                ?: Material.valueOf("BONE_MEAL")
            DyeColor.ORANGE -> Material.ORANGE_DYE
            DyeColor.MAGENTA -> Material.MAGENTA_DYE
            DyeColor.LIGHT_BLUE -> Material.LIGHT_BLUE_DYE
            DyeColor.YELLOW -> Material.values().firstOrNull { it.name == "YELLOW_DYE" }
                ?: Material.valueOf("DANDELION_YELLOW")
            DyeColor.LIME -> Material.LIME_DYE
            DyeColor.PINK -> Material.PINK_DYE
            DyeColor.GRAY -> Material.GRAY_DYE
            DyeColor.LIGHT_GRAY -> Material.LIGHT_GRAY_DYE
            DyeColor.CYAN -> Material.CYAN_DYE
            DyeColor.PURPLE -> Material.PURPLE_DYE
            DyeColor.BLUE -> Material.values().firstOrNull { it.name == "BLUE_DYE" }
                ?: Material.valueOf("LAPIS_LAZULI")
            DyeColor.BROWN -> Material.values().firstOrNull { it.name == "BROWN_DYE" }
                ?: Material.valueOf("COCOA_BEANS")
            DyeColor.GREEN -> Material.values().firstOrNull { it.name == "GREEN_DYE" }
                ?: Material.valueOf("CACTUS_GREEN")
            DyeColor.RED -> Material.values().firstOrNull { it.name == "RED_DYE" }
                ?: Material.valueOf("ROSE_RED")
            DyeColor.BLACK -> Material.values().firstOrNull { it.name == "BLACK_DYE" }
                ?: Material.valueOf("INK_SAC")
        }
    }


val DyeColor.concreteMaterial : Material
get() {
    return when(this) {
        DyeColor.WHITE -> Material.WHITE_CONCRETE
        DyeColor.ORANGE -> Material.ORANGE_CONCRETE
        DyeColor.MAGENTA -> Material.MAGENTA_CONCRETE
        DyeColor.LIGHT_BLUE -> Material.LIGHT_BLUE_CONCRETE
        DyeColor.YELLOW -> Material.YELLOW_CONCRETE
        DyeColor.LIME -> Material.LIME_CONCRETE
        DyeColor.PINK -> Material.PINK_CONCRETE
        DyeColor.GRAY -> Material.GRAY_CONCRETE
        DyeColor.LIGHT_GRAY -> Material.LIGHT_GRAY_CONCRETE
        DyeColor.CYAN -> Material.CYAN_CONCRETE
        DyeColor.PURPLE -> Material.PURPLE_CONCRETE
        DyeColor.BLUE -> Material.BLUE_CONCRETE
        DyeColor.BROWN -> Material.BROWN_CONCRETE
        DyeColor.GREEN -> Material.GREEN_CONCRETE
        DyeColor.RED -> Material.RED_CONCRETE
        DyeColor.BLACK -> Material.BLACK_CONCRETE
    }
}

fun DyeColor.toInt() =  when(this) {
    DyeColor.BLACK -> 15
    DyeColor.RED -> 14
    DyeColor.GREEN -> 13
    DyeColor.BROWN -> 12
    DyeColor.BLUE -> 11
    DyeColor.PURPLE -> 10
    DyeColor.CYAN -> 9
    DyeColor.LIGHT_GRAY -> 8
    DyeColor.GRAY -> 7
    DyeColor.PINK -> 6
    DyeColor.LIME -> 5
    DyeColor.YELLOW -> 4
    DyeColor.LIGHT_BLUE -> 3
    DyeColor.MAGENTA -> 2
    DyeColor.ORANGE -> 1
    DyeColor.WHITE -> 0
}