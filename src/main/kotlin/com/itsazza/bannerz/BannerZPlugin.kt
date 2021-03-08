package com.itsazza.bannerz

import com.itsazza.bannerz.command.BannerZAdminCommand
import com.itsazza.bannerz.command.BannerZCommand
import com.itsazza.bannerz.events.PlayerClickBannerEvent
import com.itsazza.bannerz.util.storage.BannerLibraryStorage
import com.itsazza.bannerz.util.storage.Storage
import org.bstats.bukkit.Metrics
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class BannerZPlugin : JavaPlugin() {
    companion object {
        lateinit var instance: BannerZPlugin
    }

    override fun onEnable() {
        instance = this

        if (!dataFolder.exists()) dataFolder.mkdir()
        saveDefaultConfig()

        getCommand("banner")?.setExecutor(BannerZCommand)
        getCommand("bannerzadmin")?.setExecutor(BannerZAdminCommand)
        Bukkit.getPluginManager().registerEvents(PlayerClickBannerEvent, this)

        logger.info("Loading banner categories from file...")
        BannerLibraryStorage.load()
        logger.info("Successfully loaded banner categories!")
        logger.info("Removing players with no banners from database...")
        Storage.deletePlayersWithNoBanners()
        logger.info("Removed players with no banners!")

        Metrics(this, 10408)
    }

    override fun onDisable() {
        logger.info("Saving banner categories...")
        BannerLibraryStorage.saveCategories()
        logger.info("Saved all banner categories! Goodbye!")
    }
}