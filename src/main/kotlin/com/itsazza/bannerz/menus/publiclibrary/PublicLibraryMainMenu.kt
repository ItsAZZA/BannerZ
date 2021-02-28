package com.itsazza.bannerz.menus.publiclibrary

import com.itsazza.bannerz.BannerZPlugin
import com.itsazza.bannerz.menus.Buttons
import com.itsazza.bannerz.menus.main.MainMenu
import com.itsazza.bannerz.util.storage.BannerCategory
import com.itsazza.bannerz.util.storage.BannerLibraryStorage
import de.themoep.inventorygui.GuiElementGroup
import de.themoep.inventorygui.InventoryGui
import de.themoep.inventorygui.StaticGuiElement
import org.bukkit.entity.Player

object PublicLibraryMainMenu {
    fun open(player: Player) {
        create().show(player)
    }

    fun create() : InventoryGui {
        val gui = InventoryGui(
            BannerZPlugin.instance,
            null,
            "Banner Library",
            arrayOf(
                "         ",
                " 0000000 ",
                " 0000000 ",
                " 0000000 ",
                "         ",
                "1  bc   2"
            )
        )

        val group = GuiElementGroup('0')

        BannerLibraryStorage.categories.values.forEach {
            group.addElement(createBannerCategoryButton(it))
        }

        gui.addElements(
            group,
            Buttons.close,
            Buttons.nextPage,
            Buttons.previousPage,
            Buttons.createBackButton(MainMenu.create())
        )
        gui.setCloseAction { false }
        return gui
    }

    private fun createBannerCategoryButton(bannerCategory: BannerCategory) : StaticGuiElement {
        return StaticGuiElement('@',
            bannerCategory.icon,
            {
                val player = it.event.whoClicked as Player
                PublicLibraryMenu.open(player, bannerCategory.name.toLowerCase())
                return@StaticGuiElement true
            },
            "§6§l${bannerCategory.name.capitalize()}",
            *bannerCategory.description.map { "§7$it" }.toTypedArray(),
            "§0 ",
            "§e§lCLICK §7to browse"
            )
    }
}