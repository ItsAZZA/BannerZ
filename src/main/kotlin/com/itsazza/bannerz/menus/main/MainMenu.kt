package com.itsazza.bannerz.menus.main

import com.itsazza.bannerz.BannerZPlugin
import com.itsazza.bannerz.builder.banner
import com.itsazza.bannerz.menus.Buttons.close
import com.itsazza.bannerz.menus.alphabet.AlphabetMenu
import com.itsazza.bannerz.menus.creator.BannerCreatorMenu
import com.itsazza.bannerz.menus.playerlibrary.PlayerLibraryMenu
import com.itsazza.bannerz.menus.publiclibrary.PublicLibraryMainMenu
import com.itsazza.bannerz.util.Permissions
import com.itsazza.bannerz.util.item
import com.itsazza.bannerz.util.mutateMeta
import de.themoep.inventorygui.InventoryGui
import de.themoep.inventorygui.StaticGuiElement
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

object MainMenu {
    fun open(player: Player) {
        create().show(player)
    }

    fun create(): InventoryGui {
        val gui = InventoryGui(
            BannerZPlugin.instance,
            null,
            "Banner Menu",
            arrayOf(
                "         ",
                "  01c23  ",
                "         ",
            )
        )

        gui.addElement(
            StaticGuiElement(
                '0',
                Material.BOOKSHELF.item,
                {
                    val player = it.event.whoClicked as Player
                    if (!Permissions.check(player, "bannerz.menu.public")) return@StaticGuiElement true
                    PublicLibraryMainMenu.open(player)
                    return@StaticGuiElement true
                },
                "§6§lBanner Library",
                "§7Library of pre-made",
                "§7banners",
                "§0 ",
                "§e§lCLICK §7to open"
            )
        )

        val globeBannerPatternItem =
            ItemStack(Material.values().firstOrNull { it.name == "GLOBE_BANNER_PATTERN" } ?: Material.PAPER)
        globeBannerPatternItem.mutateMeta<ItemMeta> {
            it.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS)
        }

        gui.addElement(
            StaticGuiElement(
                '1',
                globeBannerPatternItem,
                {
                    val player = it.event.whoClicked as Player
                    if (!Permissions.check(player, "bannerz.menu.alphabet")) return@StaticGuiElement true
                    AlphabetMenu.open(player)
                    return@StaticGuiElement true
                },
                "§6§lAlphabet & Numbers",
                "§7Create custom alphabet",
                "§7and number banners",
                "§0 ",
                "§e§lCLICK §7to open"
            )
        )

        gui.addElement(
            StaticGuiElement(
                '2',
                Material.WRITABLE_BOOK.item,
                {
                    val player = it.event.whoClicked as Player
                    if (!Permissions.check(player, "bannerz.menu.create")) return@StaticGuiElement true
                    BannerCreatorMenu.open(player, banner(Material.WHITE_BANNER) {})
                    return@StaticGuiElement true
                },
                "§6§lCreate Banner",
                "§7Opens a menu where you",
                "§7can create a banner",
                "§0 ",
                "§e§lCLICK §7to open"
            )
        )

        gui.addElement(
            StaticGuiElement(
                '3',
                Material.BOOK.item,
                {
                    val player = it.event.whoClicked as Player
                    if (!Permissions.check(player, "bannerz.menu.own")) return@StaticGuiElement true
                    PlayerLibraryMenu.open(player.uniqueId, player)
                    return@StaticGuiElement true
                },
                "§6§lYour banners",
                "§7Check out your saved",
                "§7banners",
                "§0 ",
                "§e§lCLICK §7to open"
            )
        )

        gui.setCloseAction { false }
        gui.addElement(close)
        return gui
    }
}