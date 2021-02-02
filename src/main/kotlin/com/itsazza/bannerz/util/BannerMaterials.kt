package com.itsazza.bannerz.util

import org.bukkit.Material
import org.bukkit.block.banner.PatternType
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BannerMeta
import java.lang.IllegalArgumentException

object BannerMaterials {
    fun getRequired(item: ItemStack): MutableMap<Material, Int> {
        val bannerMeta = item.itemMeta as? BannerMeta ?: throw IllegalArgumentException()
        val itemsNeeded = mutableMapOf<Material, Int>()

        bannerMeta.patterns.forEach { pattern ->
            itemsNeeded.merge(pattern.color.dyeMaterial, 1, Int::plus)
            if (bannerPatterns.contains(pattern.pattern)) {
                val patternItem = bannerPatterns[pattern.pattern]!!
                itemsNeeded.putIfAbsent(patternItem, 1)
            }
        }
        return itemsNeeded
    }

    fun takeRequired(items: MutableMap<Material, Int>, player: Player) {
        val filtered = items.filter { !bannerPatterns.containsValue(it.key) }
        player.inventory.takeItems(filtered)
    }

    fun takeRequired(item: ItemStack, player: Player) {
        val materials = getRequired(item).filter { !bannerPatterns.containsValue(it.key) }
        player.inventory.takeItems(materials)
    }

    private val bannerPatterns = mutableMapOf<PatternType, Material>().also {
        it[PatternType.MOJANG] = Material.MOJANG_BANNER_PATTERN
        it[PatternType.FLOWER] = Material.FLOWER_BANNER_PATTERN
        it[PatternType.CREEPER] = Material.CREEPER_BANNER_PATTERN
        it[PatternType.SKULL] = Material.SKULL_BANNER_PATTERN
        it[PatternType.PIGLIN] = Material.PIGLIN_BANNER_PATTERN
        it[PatternType.GLOBE] = Material.GLOBE_BANNER_PATTERN
    }
}