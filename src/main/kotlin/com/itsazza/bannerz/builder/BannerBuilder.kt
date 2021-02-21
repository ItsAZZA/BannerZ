package com.itsazza.bannerz.builder

import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.block.banner.Pattern
import org.bukkit.block.banner.PatternType
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BannerMeta

fun banner(base: Material, init: BannerBuilder.() -> Unit) = BannerBuilder(base).apply(init).build()

class BannerBuilder(private val base: Material) {
    val patterns = ArrayList<Pattern>()

    fun pattern(color: DyeColor, pattern: PatternType) {
        patterns += Pattern(color, pattern)
    }

    fun pattern(pattern: Pattern) {
        patterns += pattern
    }

    fun build(): ItemStack {
        val item = ItemStack(base)
        val itemMeta = item.itemMeta as BannerMeta
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS)
        itemMeta.patterns = patterns
        item.itemMeta = itemMeta
        return item
    }
}