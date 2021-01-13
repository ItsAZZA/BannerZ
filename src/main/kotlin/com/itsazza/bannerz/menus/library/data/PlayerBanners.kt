package com.itsazza.bannerz.menus.library.data

import com.itsazza.bannerz.BannerZPlugin
import com.itsazza.bannerz.util.item
import com.itsazza.bannerz.util.storage.Storage
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.util.*

data class PlayerBannerStorage(val playerBanners: MutableMap<UUID, MutableList<String>>)

object PlayerBanners {
    private val storage = Storage(BannerZPlugin.instance!!.dataFolder.resolve("banners.db"), PlayerBannerStorage::class.java)
    private val bannerStorage = storage.load() ?: PlayerBannerStorage(HashMap())

    init { storage.limit = 1 }

    fun add(playerUUID: UUID, banner: ItemStack) : Boolean {
        val values = bannerStorage.playerBanners.getOrDefault(playerUUID, mutableListOf())
        val serialized = serializeItemStack(banner) ?: return false
        values.add(serialized)
        bannerStorage.playerBanners[playerUUID] = values
        storage.save(bannerStorage)
        return true
    }

    fun getItemStack(playerUUID: UUID) : ItemStack {
        val bytes = bannerStorage.playerBanners[playerUUID]!![0]
        return deSerializeItemStack(bytes) ?: Material.WHITE_BANNER.item
    }

    fun remove(playerUUID: UUID, index: Int) : Boolean {
        bannerStorage.playerBanners[playerUUID]?.removeAt(index) ?: return false
        storage.save(bannerStorage)
        return true
    }

    fun get(playerUUID: UUID) : MutableList<String>? {
        return bannerStorage.playerBanners[playerUUID]
    }
}