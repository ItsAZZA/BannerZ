package com.itsazza.bannerz

import com.itsazza.bannerz.command.BannerZAdminCommand
import com.itsazza.bannerz.command.BannerZCommand
import com.itsazza.bannerz.events.PlayerClickBannerEvent
import com.itsazza.bannerz.util.storage.BannerLibraryStorage
import org.bstats.bukkit.Metrics
import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.InputStreamReader
import kotlin.system.measureTimeMillis

class BannerZPlugin : JavaPlugin() {
    companion object {
        lateinit var instance: BannerZPlugin
    }

    override fun onEnable() {
        instance = this

        measureTimeMillis {
            if (!dataFolder.exists()) dataFolder.mkdir()
            saveDefaultConfig()
            saveDefaultBannerCategories()

            getCommand("banner")?.setExecutor(BannerZCommand)
            getCommand("bannerzadmin")?.setExecutor(BannerZAdminCommand)
            Bukkit.getPluginManager().registerEvents(PlayerClickBannerEvent, this)
            Metrics(this, 10408)
            BannerLibraryStorage.load()
        }.also { logger.info("Loaded BannerZ in $it ms") }
    }

    private fun saveDefaultBannerCategories() {
        val bannerFiles = listOf("flags")

        for (file in bannerFiles) {
            val bannerFile = File(dataFolder, "categories/$file.yml")
            if (!bannerFile.exists()) {
                saveResource("categories/$file.yml", true)
                continue
            }
            val bannerConfiguration = YamlConfiguration.loadConfiguration(bannerFile)
            val resource = getResource("categories/$file.yml") ?: continue
            val defConfigStream = InputStreamReader(resource)
            val defaultConfiguration = YamlConfiguration.loadConfiguration(defConfigStream)
            val defaultBannerKeys = defaultConfiguration.getConfigurationSection("banners")?.getKeys(false) ?: continue
            val bannerKeys = bannerConfiguration.getConfigurationSection("banners")?.getKeys(false) ?: continue

            for (key in defaultBannerKeys) {
                if (bannerKeys.contains(key)) continue
                bannerConfiguration.set("banners.$key", defaultConfiguration.get("banners.$key"))
            }

            bannerConfiguration.save(bannerFile)
        }
    }

    override fun onDisable() {
        logger.info("Saving banner categories from memory...")
        BannerLibraryStorage.saveCategories()
        logger.info("Saved all banner categories! Goodbye!")
    }
}