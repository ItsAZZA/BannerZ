package com.itsazza.bannerz.menus.alphabet

import com.itsazza.bannerz.builder.banner
import com.itsazza.bannerz.util.bannerMaterial
import org.bukkit.DyeColor
import org.bukkit.block.banner.PatternType

enum class ColorType {
    FOREGROUND, BACKGROUND
}

class PatternTemplate(val colorType: ColorType, val patternType: PatternType)

class BannerTemplate(private val patterns: List<PatternTemplate>) {
    fun build(foregroundColor: DyeColor, backgroundColor: DyeColor) = banner(backgroundColor.bannerMaterial) {
        for (patternTemplate in patterns) {
            val color = when(patternTemplate.colorType) {
                ColorType.FOREGROUND -> foregroundColor
                ColorType.BACKGROUND -> backgroundColor
            }
            pattern(color, patternTemplate.patternType)
        }
    }
}

class BannerTemplateBuilder {
    internal val patterns = mutableListOf<PatternTemplate>()

    fun pattern(colorType: ColorType, patternType: PatternType) {
        patterns += PatternTemplate(colorType, patternType)
    }
}

fun bannerTemplate(block: BannerTemplateBuilder.() -> Unit): BannerTemplate {
    val builder = BannerTemplateBuilder()
    builder.block()
    return BannerTemplate(builder.patterns)
}