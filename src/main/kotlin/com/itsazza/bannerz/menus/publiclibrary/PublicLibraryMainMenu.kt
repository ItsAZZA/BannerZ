package com.itsazza.bannerz.menus.publiclibrary

import com.itsazza.bannerz.BannerZPlugin
import com.itsazza.bannerz.menus.Buttons
import com.itsazza.bannerz.menus.main.MainMenu
import com.itsazza.bannerz.util.Sounds
import com.itsazza.bannerz.util.capitalizeFirst
import com.itsazza.bannerz.util.item
import com.itsazza.bannerz.util.storage.BannerCategory
import com.itsazza.bannerz.util.storage.BannerLibraryStorage
import de.themoep.inventorygui.GuiElementGroup
import de.themoep.inventorygui.InventoryGui
import de.themoep.inventorygui.StaticGuiElement
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

object PublicLibraryMainMenu {
    fun open(player: Player) {
        create().show(player)
    }

    fun create(): InventoryGui {
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
                "1  bcs  2"
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
            Buttons.createBackButton(MainMenu.create()),
            searchButton
        )
        gui.setCloseAction { false }
        return gui
    }

    private fun createBannerCategoryButton(bannerCategory: BannerCategory): StaticGuiElement {
        return StaticGuiElement(
            '@',
            bannerCategory.icon,
            {
                val player = it.event.whoClicked as Player
                BannerLibraryStorage.categories[bannerCategory.name.replace(" ", "").lowercase()]?.let { category ->
                    PublicLibraryMenu.open(player, category.name, category.banners.values.toList())
                    return@StaticGuiElement true
                }
                Sounds.play(player, Sound.ENTITY_VILLAGER_NO)
                player.sendMessage("§cBanner category not found!")
                return@StaticGuiElement true
            },
            "§6§l${bannerCategory.name.capitalizeFirst()}",
            *bannerCategory.description.toTypedArray(),
            "§0 ",
            "§e§lCLICK §7to browse"
        )
    }

    private val searchButton: StaticGuiElement
        get() = StaticGuiElement(
            's',
            Material.OAK_SIGN.item,
            {
                val player = it.event.whoClicked as Player
                SearchConversation.start(player)
                it.gui.destroy()
                return@StaticGuiElement true
            },
            "§6§lSearch for Banner",
            "§0 ",
            "§7Search for a banner",
            "§7by name",
            "§0 ",
            "§6§lCLICK §7to search"
        )
}