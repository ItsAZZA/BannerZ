package com.itsazza.bannerz.menus.playerlibrary.data

import com.itsazza.bannerz.util.storage.Storage
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import java.util.*

object PlayerBanners {
    fun remove(playerUUID: UUID, bannerIndex: Int): Boolean {
        val player = playerUUID.toString()
        val values = Storage.loadPlayer(player) ?: return false
        values.removeAt(bannerIndex)
        Storage.savePlayer(player, values)
        return true
    }

    fun add(playerUUID: UUID, banner: ItemStack): Boolean {
        val player = playerUUID.toString()
        val values = Storage.loadPlayer(player) ?: mutableListOf()
        banner.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS)
        val serialized = serializeItemStack(banner)
        values.add(serialized)
        Storage.savePlayer(player, values)
        return true
    }

    fun get(playerUUID: UUID): MutableList<String>? {
        return Storage.loadPlayer(playerUUID.toString())
    }
}