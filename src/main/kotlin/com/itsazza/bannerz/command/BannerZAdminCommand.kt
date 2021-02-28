package com.itsazza.bannerz.command

import com.itsazza.bannerz.util.checkPermission
import com.itsazza.bannerz.util.isBanner
import com.itsazza.bannerz.util.storage.BannerLibraryStorage
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag

object BannerZAdminCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        if (sender !is Player) return true

        if (!checkPermission(sender, "bannerz.admin")) return true

        if (args.isEmpty()) {
            val message = """
                §ePossible subcommands:
                §f- /bza add <category> : Add banner in hand to category
                §f- /bza remove <category> <index> : Remove banner index in category
                §f- /bza category create <category> : Add category
                §f- /bza category remove <category> : Remove category
                §f- /bza category icon <category> : Set category icon
                §f- /bza category description <category> <desc> : Set category description
            """.trimIndent()
            sender.sendMessage(message)
            return true
        }

        when (args[0]) {
            "add" -> {
                if (args.size < 2) {
                    sender.sendMessage("§cUsage: /bza create <category>")
                    return true
                }

                val category = args[1].toLowerCase()
                if (!BannerLibraryStorage.categoryExists(category)) {
                    sender.sendMessage("§cNo category found with name \"$category\"!")
                    return true
                }

                val banner = sender.inventory.itemInMainHand.clone()
                if (!isBanner(banner)) {
                    sender.sendMessage("§cYou must be holding a banner to add it!")
                    return true
                }

                banner.amount = 1
                banner.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS)
                sender.sendMessage("§eAdded banner to category $category")
                BannerLibraryStorage.addBanner(banner, category)
                return true
            }
            "remove" -> {
                if (args.size < 3) {
                    sender.sendMessage("§cUsage: /bza remove <category> <index>")
                    return true
                }

                val category = args[1].toLowerCase()
                val index = args[2].toInt()

                if (!BannerLibraryStorage.categoryExists(category)) {
                    sender.sendMessage("§cNo category found with name \"$category\"!")
                    return true
                }

                if (!BannerLibraryStorage.categoryHasBannerIndex(category, index)) {
                    sender.sendMessage("§cA banner at index $index in category $category does not exist!")
                    return true
                }

                sender.sendMessage("§eRemoved banner at index $index in category $category")
                BannerLibraryStorage.removeBanner(category, index)
                return true
            }
            "category" -> {
                if (args.size < 2) {
                    sender.sendMessage("§cUsage: /bza category <add|remove|icon|description> [args]")
                    return true
                }

                when (args[1]) {
                    "create" -> {
                        if (args.size < 3) {
                            sender.sendMessage("§cUsage: /bza category add <name>")
                            return true
                        }

                        val name = args.drop(2).joinToString(" ")
                        BannerLibraryStorage.addCategory(name).also { result ->
                            if (result) {
                                sender.sendMessage("§eAdded new category with the name $name")
                            } else {
                                sender.sendMessage("§cAn error occurred adding new category. Please make sure a category with this name doesn't already exist!")
                            }
                        }
                        return true
                    }
                    "remove" -> {
                        if (args.size < 3) {
                            sender.sendMessage("§cUsage: /bza category remove <category>")
                            return true
                        }

                        val category = args[2].toLowerCase()
                        BannerLibraryStorage.removeCategory(category).also { response ->
                            if (response) {
                                sender.sendMessage("§eRemoved category $category")
                            } else {
                                sender.sendMessage("§cAn error occurred trying to remove category $category. Please make sure this category exists!")
                            }
                            return true
                        }
                    }
                    "icon" -> {
                        if (args.size < 3) {
                            sender.sendMessage("§cUsage: /bza category icon <category>")
                            return true
                        }

                        val category = args[2].toLowerCase()
                        val item = sender.inventory.itemInMainHand

                        BannerLibraryStorage.setCategoryIcon(category, item).also { response ->
                            if (response) {
                                sender.sendMessage("§eChanged icon for category $category")
                            } else {
                                sender.sendMessage("§cAn error occurred trying to set the category icon. Please make sure this category exists!")
                            }
                            return true
                        }
                    }
                    "description", "desc" -> {
                        if (args.size < 4) {
                            sender.sendMessage("§cUsage: /bza category description <description>")
                            return true
                        }

                        val category = args[2].toLowerCase()
                        val description = args.drop(3).chunked(4) { it.joinToString(" ") }.toCollection(ArrayList())

                        BannerLibraryStorage.setCategoryDescription(category, description).also { response ->
                            if (response) {
                                sender.sendMessage("§eSet a description for category $category")
                            } else {
                                sender.sendMessage("§cAn error occurred trying to set the category description. Please make sure this category exists!")
                            }
                            return true
                        }
                    }
                }
            }
        }
        return true
    }
}