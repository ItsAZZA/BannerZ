package com.itsazza.bannerz.menus.main

import com.itsazza.bannerz.BannerZPlugin
import com.itsazza.bannerz.builder.banner
import com.itsazza.bannerz.menus.alphabet.AlphaBetMenu
import com.itsazza.bannerz.menus.closeButton
import com.itsazza.bannerz.menus.creator.BannerCreatorMenu
import com.itsazza.bannerz.menus.creator.CreatorMode
import com.itsazza.bannerz.util.item
import de.themoep.inventorygui.InventoryGui
import de.themoep.inventorygui.StaticGuiElement
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import sun.audio.AudioPlayer.player

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
            "§6§lBanner Library",
            "§7Library of pre-made",
            "§7banners",
            "§0 ",
            "§e§lCLICK §7to open"
        ))

        val globeBannerPatternItem = ItemStack(Material.GLOBE_BANNER_PATTERN)
        globeBannerPatternItem.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS)

        gui.addElement(StaticGuiElement(
            '1',
            globeBannerPatternItem,
            {
                val player = it.event.whoClicked as Player
                AlphaBetMenu.open(player)
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
            "§6§lYour banners",
            "§7Check out your saved",
            "§7banners",
            "§0 ",
            "§e§lCLICK §7to open"
        ))

        gui.setCloseAction { false }
        gui.addElement(closeButton)
        return gui
    }
}