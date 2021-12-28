package com.itsazza.bannerz.builder

import com.itsazza.bannerz.util.mutateMeta
import org.bukkit.ChatColor
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.block.banner.Pattern
import org.bukkit.block.banner.PatternType
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BannerMeta

fun banner(base: Material, init: BannerBuilder.() -> Unit) = BannerBuilder(base).apply(init).build()
fun banner(base: Material, name: String, init: BannerBuilder.() -> Unit) = BannerBuilder(base, name).apply(init).build()

class BannerBuilder(private val base: Material, private val name: String? = null) {
    private val patterns = ArrayList<Pattern>()

    fun pattern(color: DyeColor, pattern: PatternType) {
        patterns += Pattern(color, pattern)
    }

    fun pattern(pattern: Pattern) {
        patterns += pattern
    }

    fun build(): ItemStack {
        return ItemStack(base).mutateMeta<BannerMeta> {
            it.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS)
            it.patterns = patterns
            if (name != null) {
                it.setDisplayName(ChatColor.translateAlternateColorCodes('&', name))
            }
        }
    }
}