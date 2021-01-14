package com.itsazza.bannerz.menus.library.data

import com.itsazza.bannerz.BannerZPlugin
import com.itsazza.bannerz.util.storage.Storage
import org.bukkit.inventory.ItemStack
import java.util.*

object PlayerBanners {
    private val storage = Storage(BannerZPlugin.instance!!.dataFolder.resolve("banners.db"))

    fun remove(playerUUID: UUID, bannerIndex: Int): Boolean {
        val player = playerUUID.toString()
        val values = storage.loadPlayer(player) ?: return false
        values.removeAt(bannerIndex)
        storage.savePlayer(player, values)
        return true
    }

    fun add(playerUUID: UUID, banner: ItemStack): Boolean {
        val player = playerUUID.toString()
        val values = storage.loadPlayer(player) ?: mutableListOf()
        val serialized = serializeItemStack(banner) ?: return false
        values.add(serialized)
        storage.savePlayer(player, values)
        return true
    }

    fun get(playerUUID: UUID): MutableList<String>? {
        return storage.loadPlayer(playerUUID.toString())
    }
}