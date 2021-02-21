package com.itsazza.bannerz.command

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object BannerZAdminCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        if (sender !is Player) return true

        // /bannerzadmin, /bza
        /*
            /bza add <category> -> Adds banner in hand to the category
            /bza remove <category> <id> -> Removes banner ID in category
            /bza category add <category> -> Adds new category
            /bza category remove <category> -> Remove category
            /bza icon <category> <icon>
            /bza description <desc>
         */

        return true
    }
}