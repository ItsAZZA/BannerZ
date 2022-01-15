package com.itsazza.bannerz.menus.publiclibrary

import com.itsazza.bannerz.BannerZPlugin
import com.itsazza.bannerz.menus.Buttons
import com.itsazza.bannerz.menus.creator.BannerCreatorMenu
import com.itsazza.bannerz.util.Sounds
import com.itsazza.bannerz.util.capitalizeFirst
import com.itsazza.bannerz.util.checkBanner
import de.themoep.inventorygui.GuiElementGroup
import de.themoep.inventorygui.InventoryGui
import de.themoep.inventorygui.StaticGuiElement
import org.bukkit.ChatColor
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object PublicLibraryMenu {
    fun open(player: Player, name: String, banners: List<ItemStack>): Boolean {
        create(banners, name).show(player)
        return true
    }

    fun create(banners: List<ItemStack>, name: String): InventoryGui {
        val gui = InventoryGui(
            BannerZPlugin.instance,
            null,
            name.capitalizeFirst(),
            arrayOf(
                "000000000",
                "000000000",
                "000000000",
                "000000000",
                "000000000",
                "1  bcs  2",
            )
        )

        val group = GuiElementGroup('0')

        banners.forEach {
            group.addElement(createBannerButton(it))
        }

        with (Buttons) {
            gui.addElements(
                group,
                previousPage,
                nextPage,
                close,
                createBackButton(PublicLibraryMainMenu.create()),
            )
        }

        gui.setCloseAction { false }
        return gui
    }

    private fun createBannerButton(banner: ItemStack): StaticGuiElement {
        val bannerName = if (banner.itemMeta!!.hasDisplayName()) ChatColor.translateAlternateColorCodes(
            '&',
            banner.itemMeta!!.displayName
        ) else "§6Banner"

        return StaticGuiElement(
            '@',
            banner,
            {
                val player = it.event.whoClicked as Player
                when {
                    it.event.isLeftClick -> {
                        if (!checkBanner(banner, player)) return@StaticGuiElement true
                        player.inventory.addItem(banner)
                        Sounds.play(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP)
                        return@StaticGuiElement true
                    }
                    it.event.isRightClick -> {
                        BannerCreatorMenu.open(player, banner)
                        return@StaticGuiElement true
                    }
                    else -> return@StaticGuiElement true
                }
            },
            "§6$bannerName",
            "§0 ",
            "§e§lL-CLICK §7to get",
            "§e§lR-CLICK §7to edit"
        )
    }
}