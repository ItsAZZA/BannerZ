package com.itsazza.bannerz.command

import com.itsazza.bannerz.builder.banner
import com.itsazza.bannerz.util.bannerMaterial
import com.itsazza.bannerz.menus.creator.BannerCreatorMenu
import com.itsazza.bannerz.menus.creator.CreatorMode
import com.itsazza.bannerz.menus.main.MainMenu
import com.itsazza.bannerz.util.isBanner
import org.bukkit.DyeColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object BannerZCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, p2: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("§cThis command must be executed as a player!")
            return true
        }

        /*if (!sender.hasPermission("bannerz.menu")) {
            sender.sendMessage("§cInsufficient permissions: bannerz.menu")
            return true
        }*/

        if (args.isEmpty()) {
            MainMenu.open(sender)
            return true
        }

        when (args[0].toLowerCase()) {
            "create", "creator" -> {
                BannerCreatorMenu.open(sender, banner(DyeColor.WHITE.bannerMaterial){})
            }
            "edit", "editor" -> {
                val block = sender.inventory.itemInMainHand.clone()

                if (!isBanner(block.type)) {
                    sender.sendMessage("§cYou must be holding a banner!")
                    return true
                }

                BannerCreatorMenu.open(sender, block)
                return true
            }
        }
        return true
    }
}