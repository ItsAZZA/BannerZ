package com.itsazza.bannerz.util

import com.itsazza.bannerz.BannerZPlugin
import org.bukkit.GameMode
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BannerMeta

fun checkBanner(item: ItemStack, player: Player): Boolean {
    val config = BannerZPlugin.instance.config
    val bannerMeta = item.itemMeta as BannerMeta
    val patterns = bannerMeta.patterns.size
    val maxPatterns = config.getInt("settings.survival.patternLimit")
    val playerMaxPatterns = Permissions.getNumberFromPermission(player, "bannerz.maxpatterns.", maxPatterns)

    if (playerMaxPatterns < patterns && patterns > maxPatterns) {
        player.sendMessage("§cYou don't have permission to get a banner with $patterns patterns!")
        Sounds.play(player, Sound.ENTITY_VILLAGER_NO)
        return false
    }


    if (config.getBoolean("settings.survival.enabled")) {
        if (config.getBoolean("settings.survival.bypassInCreative")
            && player.gameMode == GameMode.CREATIVE
            || player.hasPermission("bannerz.crafting.bypassmaterialcost")) return true

        val materials = BannerMaterials.getRequired(item)
        val inventory = player.inventory

        if (!inventory.hasItems(materials)) {
            Sounds.play(player, Sound.ENTITY_VILLAGER_NO)
            player.sendMessage("§cYou do not have the items to craft this item!")
            val needed = materials.map { "${it.value}x ${it.key.name.beautify()}" }.joinToString()
            player.sendMessage("§eYou need: §7$needed")
            return false
        }

        BannerMaterials.takeRequired(materials, player)
    }
    return true
}