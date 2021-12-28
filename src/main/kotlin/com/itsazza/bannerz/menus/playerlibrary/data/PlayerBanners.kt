package com.itsazza.bannerz.menus.playerlibrary.data

import com.itsazza.bannerz.util.storage.Storage
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.collections.HashMap

object PlayerBanners {
    fun remove(playerUUID: UUID, bannerIndex: Int): Boolean {
        Storage.removeBanner(playerUUID.toString(), bannerIndex)
        return true
    }

    fun add(playerUUID: UUID, banner: ItemStack): Boolean {
        Storage.addBanner(playerUUID.toString(), banner)
        return true
    }

    fun get(playerUUID: UUID): HashMap<Int, ItemStack>? {
        return Storage.loadPlayer(playerUUID.toString())
    }
}