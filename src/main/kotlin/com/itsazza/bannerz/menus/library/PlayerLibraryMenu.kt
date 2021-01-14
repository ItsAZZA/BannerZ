package com.itsazza.bannerz.menus.library

import com.itsazza.bannerz.BannerZPlugin
import com.itsazza.bannerz.menus.Buttons
import com.itsazza.bannerz.menus.Buttons.close
import com.itsazza.bannerz.menus.Buttons.createBackButton
import com.itsazza.bannerz.menus.Buttons.nextPage
import com.itsazza.bannerz.menus.Buttons.previousPage
import com.itsazza.bannerz.menus.creator.BannerCreatorMenu
import com.itsazza.bannerz.menus.library.data.PlayerBanners
import com.itsazza.bannerz.menus.library.data.deSerializeItemStack
import com.itsazza.bannerz.menus.main.MainMenu
import com.itsazza.bannerz.util.Sounds
import com.itsazza.bannerz.util.item
import de.themoep.inventorygui.GuiElementGroup
import de.themoep.inventorygui.InventoryGui
import de.themoep.inventorygui.StaticGuiElement
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object PlayerLibraryMenu {
    private val plugin = BannerZPlugin.instance

    fun open(player: Player) {
        val menu = create(player)
        if(menu == null) {
            player.sendMessage("§cNo saved banners found!")
            Sounds.play(player, Sound.ENTITY_VILLAGER_NO)
            return
        }
        menu.show(player)
    }

    fun create(player: Player): InventoryGui? {
        val gui = InventoryGui(
            plugin,
            null,
            "Your Banner Library",
            libraryMenuTemplate
        )

        val playerUUID = player.uniqueId
        val banners = PlayerBanners.get(playerUUID)
        if (banners.isNullOrEmpty()) return null
        val group = GuiElementGroup('0')

        for((i, banner) in banners.withIndex()) {
            group.addElement(createBannerLibraryButton(deSerializeItemStack(banner), i))
        }

        gui.addElement(group)
        gui.addElement(previousPage)
        gui.addElement(nextPage)
        gui.addElement(createBackButton(MainMenu.create()))
        gui.addElement(close)
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
                } else {
                    createBannerGetMenu(banner, index).show(player)
                    return@StaticGuiElement true
                }
            },
            "§6§lBanner",
            "§7Banner #$index in your",
            "§7personal banner library",
            "§0 ",
            "§e§lL-CLICK §7to get",
            "§e§lR-CLICK §7for more options"
            )
    }

    private fun createBannerGetMenu(banner: ItemStack, index: Int) : InventoryGui {
        val gui = InventoryGui(
            plugin,
            null,
            "Banner",
            arrayOf(
                "    p    ",
                "   ger   ",
                "    b    "
            )
        )

        gui.addElement(BannerCreatorMenu.createGiveCommandButton(banner))
        gui.addElement(StaticGuiElement('e',
            Material.WRITABLE_BOOK.item,
            {
                val player = it.event.whoClicked as Player
                BannerCreatorMenu.open(player, banner)
                return@StaticGuiElement true
            },
            "§6§lEdit Banner",
            "§7Open this banner in the",
            "§7banner editor menu",
            "§0 ",
            "§e§lCLICK §7to select"
        ))
        gui.addElement(StaticGuiElement('r',
            Material.RED_CONCRETE.item,
            {
                val player = it.event.whoClicked as Player
                PlayerBanners.remove(player.uniqueId, index)
                Sounds.play(player, Sound.ENTITY_VILLAGER_YES)
                player.sendMessage("§eBanner removed from personal library!")
                it.gui.close()
                return@StaticGuiElement true
            },
            "§c§lDelete Banner",
            "§7Remove the banner from",
            "§7your personal library",
            "§0 ",
            "§cThis action cannot be undone!",
            "§0 ",
            "§e§lCLICK §7to remove"
            ))

        gui.addElement(Buttons.backInHistory)
        gui.setCloseAction { false }
        return gui
    }
}