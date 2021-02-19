package com.itsazza.bannerz.util

import com.itsazza.bannerz.BannerZPlugin
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BannerMeta

fun checkSurvivalCrafting(item: ItemStack, player: Player) : Boolean {
    val config = BannerZPlugin.instance!!.config

    if (config.getBoolean("settings.survival.enabled")) {
        val bannerMeta = item.itemMeta as BannerMeta
        val patternLimit = config.getInt("settings.survival.patternLimit")
        if (bannerMeta.patterns.size > patternLimit) {
            Sounds.play(player, Sound.ENTITY_VILLAGER_NO)
            player.sendMessage("§cThis banner has too many patterns! Limit is $patternLimit patterns.")
            return false
        }
        val materials = BannerMaterials.getRequired(item)
        val inventory = player.inventory
        if (!inventory.hasItems(materials)) {
            Sounds.play(player, Sound.ENTITY_VILLAGER_NO)
            player.sendMessage("§cYou do not have the items to craft this item!")
            val needed = materials.map { "${it.value}x ${it.key.name.replace("_", " ").toLowerCase()}" }.joinToString()
            player.sendMessage("§eYou need: §7$needed")
            return false
        }
        BannerMaterials.takeRequired(materials, player)
    }
    return true
}