package com.itsazza.bannerz

import com.itsazza.bannerz.command.BannerZCommand
import org.bukkit.plugin.java.JavaPlugin

class BannerZPlugin : JavaPlugin() {
    companion object {
        var instance: BannerZPlugin? = null
            private set
    }

    override fun onEnable() {
        instance = this
        getCommand("banner")?.setExecutor(BannerZCommand)
    }
}