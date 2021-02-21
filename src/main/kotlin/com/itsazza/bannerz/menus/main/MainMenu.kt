package com.itsazza.bannerz.menus.main

import com.itsazza.bannerz.BannerZPlugin
import com.itsazza.bannerz.builder.banner
import com.itsazza.bannerz.menus.Buttons.close
import com.itsazza.bannerz.menus.alphabet.AlphabetMenu
import com.itsazza.bannerz.menus.creator.BannerCreatorMenu
import com.itsazza.bannerz.menus.playerlibrary.PlayerLibraryMenu
import com.itsazza.bannerz.util.item
import de.themoep.inventorygui.InventoryGui
import de.themoep.inventorygui.StaticGuiElement
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

object MainMenu {
    fun open(player: Player) {
        create().show(player)
    }

    fun create(): InventoryGui {
        val gui = InventoryGui(
           BannerZPlugin.instance,
           null,
           "Banner Menu",
           mainMenuTemplate
        )

        gui.addElement(StaticGuiElement(
            '0',
            Material.BOOKSHELF.item,
            {
                val player = it.event.whoClicked as Player
                player.sendMessage("§6This feature is coming soon!")
                return@StaticGuiElement true
            },
            "§6§lBanner Library",
            "§7Library of pre-made",
            "§7banners",
            "§0 ",
            "§cComing soon(ish)™!"
        ))

        val globeBannerPatternItem = ItemStack(Material.GLOBE_BANNER_PATTERN)
        globeBannerPatternItem.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS)

        gui.addElement(StaticGuiElement(
            '1',
            globeBannerPatternItem,
            {
                val player = it.event.whoClicked as Player
                AlphabetMenu.open(player)
                return@StaticGuiElement true
            },
            "§6§lAlphabet & Numbers",
            "§7Create custom alphabet",
            "§7and number banners",
            "§0 ",
            "§e§lCLICK §7to open"
        ))

        gui.addElement(StaticGuiElement(
            '2',
            Material.WRITABLE_BOOK.item,
            {
                val player = it.event.whoClicked as Player
                BannerCreatorMenu.open(player, banner(Material.WHITE_BANNER){})
                return@StaticGuiElement true
            },
            "§6§lCreate Banner",
            "§7Opens a menu where you",
            "§7can create a banner",
            "§0 ",
            "§e§lCLICK §7to open"
            ))

        gui.addElement(StaticGuiElement(
            '3',
            Material.BOOK.item,
            {
                val player = it.event.whoClicked as Player
                PlayerLibraryMenu.open(player)
                return@StaticGuiElement true
            },
            "§6§lYour banners",
            "§7Check out your saved",
            "§7banners",
            "§0 ",
            "§e§lCLICK §7to open"
        ))

        gui.setCloseAction { false }
        gui.addElement(close)
        return gui
    }
}