package com.itsazza.bannerz.command

import com.itsazza.bannerz.util.isBanner
import com.itsazza.bannerz.util.storage.BannerLibraryStorage
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.BannerMeta

object BannerZAdminCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        if (sender !is Player) return true

        // /bannerzadmin, /bza
        /*
            /bza add <category> -> Adds banner in hand to the category
            /bza remove <category> <id> -> Removes banner ID in category
            /bza category add <category> -> Adds new category
            /bza category remove <category> -> Remove category
            /bza category icon <category> <icon>
            /bza category description <desc>
         */

        val item = sender.inventory.itemInMainHand
        if (isBanner(item.type)) return true
        val bannerMeta = item.itemMeta as BannerMeta
        BannerLibraryStorage.broadcastBytes(bannerMeta)

        return true
    }
}