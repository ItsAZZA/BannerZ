package com.itsazza.bannerz.menus.library

import com.itsazza.bannerz.BannerZPlugin
import com.itsazza.bannerz.menus.closeButton
import com.itsazza.bannerz.menus.createBackButton
import com.itsazza.bannerz.menus.library.data.PlayerBanners
import com.itsazza.bannerz.menus.library.data.deSerializeItemStack
import com.itsazza.bannerz.menus.main.MainMenu
import com.itsazza.bannerz.util.Sounds
import de.themoep.inventorygui.GuiElementGroup
import de.themoep.inventorygui.InventoryGui
import de.themoep.inventorygui.StaticGuiElement
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object LibraryMenu {
    fun open(player: Player) : Boolean {
        create(player)?.show(player) ?: return false
        return true
    }

    private fun create(player: Player): InventoryGui? {
        val gui = InventoryGui(
            BannerZPlugin.instance,
            null,
            "Your Banner Library",
            libraryMenuTemplate
        )

        val playerUUID = player.uniqueId
        val banners = PlayerBanners.get(playerUUID) ?: return null
        val group = GuiElementGroup('0')

        for((i, banner) in banners.withIndex()) {
            group.addElement(createBannerLibraryButton(deSerializeItemStack(banner), i))
        }

        gui.addElement(group)
        // TODO: Add next and previous page buttons
        gui.addElement(createBackButton(MainMenu.create()))
        gui.addElement(closeButton)
        gui.setCloseAction { false }
        return gui
    }

    private fun createBannerLibraryButton(banner: ItemStack, index: Int) : StaticGuiElement {
        return StaticGuiElement('@',
            banner,
            {
                val player = it.event.whoClicked as Player
                if (it.event.isLeftClick) {
                    player.inventory.addItem(banner)
                    Sounds.play(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP)
                    return@StaticGuiElement true
                }
                return@StaticGuiElement true
            },
            "§6§lBanner #$index",
            "§7Banner #$index in your",
            "§7personal banner library",
            "§0 ",
            "§e§lL-CLICK §7to get",
            "§e§lR-CLICK §7for more options"
            )
    }
}