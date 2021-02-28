package com.itsazza.bannerz.menus.publiclibrary

import com.itsazza.bannerz.BannerZPlugin
import com.itsazza.bannerz.menus.Buttons
import com.itsazza.bannerz.menus.creator.BannerCreatorMenu
import com.itsazza.bannerz.menus.playerlibrary.libraryMenuTemplate
import com.itsazza.bannerz.util.Sounds
import com.itsazza.bannerz.util.checkSurvivalCrafting
import com.itsazza.bannerz.util.storage.BannerLibraryStorage
import de.themoep.inventorygui.GuiElementGroup
import de.themoep.inventorygui.InventoryGui
import de.themoep.inventorygui.StaticGuiElement
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object PublicLibraryMenu {
    fun open(player: Player, category: String) : Boolean {
        create(category)?.show(player) ?: return false
        return true
    }

    fun create(category: String): InventoryGui? {
        val gui = InventoryGui(
            BannerZPlugin.instance,
            null,
            category.capitalize(),
            libraryMenuTemplate
        )

        val group = GuiElementGroup('0')

        val bannerCategory = BannerLibraryStorage.getCategory(category) ?: return null
        bannerCategory.banners.forEach {
            group.addElement(createBannerButton(it, category))
        }

        gui.addElements(
            group,
            Buttons.previousPage,
            Buttons.nextPage,
            Buttons.close,
            Buttons.createBackButton(PublicLibraryMainMenu.create())
        )

        gui.setCloseAction { false }
        return gui
    }

    private fun createBannerButton(banner: ItemStack, category: String): StaticGuiElement {
        var bannerName = "Banner"
        if (banner.itemMeta.hasDisplayName()) {
            bannerName = banner.itemMeta.displayName
        }

        return StaticGuiElement('@',
            banner,
            {
                val player = it.event.whoClicked as Player
                when {
                    it.event.isLeftClick -> {
                        if (!checkSurvivalCrafting(banner, player)) return@StaticGuiElement true
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
            "§8${category.capitalize()}",
            "§0 ",
            "§e§lL-CLICK §7to get",
            "§e§lR-CLICK §7to edit"
        )
    }
}