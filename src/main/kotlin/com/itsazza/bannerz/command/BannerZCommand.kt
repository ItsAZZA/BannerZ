package com.itsazza.bannerz.command

import com.itsazza.bannerz.BannerZPlugin
import com.itsazza.bannerz.builder.banner
import com.itsazza.bannerz.menus.alphabet.AlphabetMenu
import com.itsazza.bannerz.util.bannerMaterial
import com.itsazza.bannerz.menus.creator.BannerCreatorMenu
import com.itsazza.bannerz.menus.playerlibrary.PlayerLibraryMenu
import com.itsazza.bannerz.menus.playerlibrary.data.PlayerBanners
import com.itsazza.bannerz.menus.main.MainMenu
import com.itsazza.bannerz.menus.publiclibrary.PublicLibraryMainMenu
import com.itsazza.bannerz.menus.publiclibrary.PublicLibraryMenu
import com.itsazza.bannerz.util.Sounds
import com.itsazza.bannerz.util.checkPermission
import com.itsazza.bannerz.util.isBanner
import org.bukkit.*
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

        if (!checkPermission(sender, "bannerz.menu")) return true

        if (args.isEmpty()) {
            MainMenu.open(sender)
            return true
        }

        when (args[0].toLowerCase()) {
            "reload" -> {
                val plugin = BannerZPlugin.instance
                plugin.reloadConfig()
                sender.sendMessage("§eReloaded config!")
                return true
            }
            "create", "creator" -> {
                if (!checkPermission(sender, "bannerz.menu.create")) return true
                BannerCreatorMenu.open(sender, banner(DyeColor.WHITE.bannerMaterial){})
                return true
            }
            "edit", "editor" -> {
                if (!checkPermission(sender, "bannerz.menu.edit")) return true
                val block = sender.inventory.itemInMainHand.clone()

                if (!isBanner(block.type)) {
                    sender.sendMessage("§cYou must be holding a banner!")
                    return true
                }

                BannerCreatorMenu.open(sender, block)
                return true
            }
            "library", "bannerlibrary", "bl" -> {
                if (!checkPermission(sender, "bannerz.menu.library")) return true
                if (args.size < 2) {
                    PublicLibraryMainMenu.open(sender)
                    return true
                }

                val library = args[1].toLowerCase()
                PublicLibraryMenu.open(sender, library)
                return true
            }
            "playerlibrary", "player", "pl" -> {
                if(args.size >= 2) {
                    if (!checkPermission(sender, "bannerz.menu.playerlibrary")) return true
                    val offlinePlayer = Bukkit.getOfflinePlayerIfCached(args[1])
                    if (offlinePlayer == null) {
                        sender.sendMessage("§cNo player found with that name!")
                        return true
                    }
                    PlayerLibraryMenu.open(offlinePlayer.uniqueId, sender)
                    return true
                }
            }
            "mine", "mybanners", "my" -> {
                if (!checkPermission(sender, "bannerz.menu.mybanners")) return true
                PlayerLibraryMenu.open(sender.uniqueId, sender)
                return true
            }
            "save" -> {
                if (!checkPermission(sender, "bannerz.menu.save")) return true
                val block = sender.inventory.itemInMainHand.clone()
                block.amount = 1

                if (!isBanner(block.type)) {
                    sender.sendMessage("§cYou must be holding a banner!")
                    return true
                }

                val response = PlayerBanners.add(sender.uniqueId, block)
                if (response) {
                    Sounds.play(sender, Sound.ENTITY_VILLAGER_YES)
                    sender.sendMessage("§eBanner added to personal library!")
                } else {
                    sender.sendMessage("§cSomething went wrong adding the banner!")
                }
            }
            "alphabet", "number" -> {
                if (!checkPermission(sender, "bannerz.menu.alphabet")) return true
                AlphabetMenu.open(sender)
                return true
            }
            "name" -> {
                if (!checkPermission(sender, "banners.name")) return true

                if (args.size < 2) {
                    sender.sendMessage("§cUsage: /bannerz name <name>")
                }

                val item = sender.inventory.itemInMainHand

                if (!isBanner(item)) {
                    sender.sendMessage("§cYou must be holding a banner!")
                    return true
                }

                val itemMeta = item.itemMeta
                val name = ChatColor.translateAlternateColorCodes('&', args.drop(1).joinToString(" "))
                itemMeta.setDisplayName(name)
                item.itemMeta = itemMeta
            }
            else -> {
                sender.sendMessage("""
                    §ePossible subcommands:
                    §f- /bannerz create : Open banner creator
                    §f- /bannerz edit : Open banner in hand in the editor
                    §f- /bannerz save : Save banner in hand to personal library
                    §f- /bannerz alphabet : Open alphabet & numerals creator
                    §f- /bannerz mine : Show your personal banner library
                    §f- /banners library [category] : Open banner library category
                    §f- /bannerz player <player> : Show player's banner library
                    §f- /bannerz name <name> : Name an item in your hand
                    §f- /bannerz reload : Reload config
                """.trimIndent())
            }
        }
        return true
    }
}