package com.itsazza.bannerz

import com.itsazza.bannerz.command.BannerZCommand
import com.itsazza.bannerz.events.PlayerClickBannerEvent
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class BannerZPlugin : JavaPlugin() {
    companion object {
        var instance: BannerZPlugin? = null
            private set
    }

    override fun onEnable() {
        instance = this

        if (!dataFolder.exists()) dataFolder.mkdir()

        getCommand("banner")?.setExecutor(BannerZCommand)
        Bukkit.getPluginManager().registerEvents(PlayerClickBannerEvent, this)
    }
}