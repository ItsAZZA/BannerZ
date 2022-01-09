package com.itsazza.bannerz.menus

import com.itsazza.bannerz.menus.creator.BannerCreatorMenu
import com.itsazza.bannerz.menus.playerlibrary.data.PlayerBanners
import com.itsazza.bannerz.util.*
import de.themoep.inventorygui.GuiBackElement
import de.themoep.inventorygui.GuiPageElement
import de.themoep.inventorygui.InventoryGui
import de.themoep.inventorygui.StaticGuiElement
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack

object Buttons {
    fun createBackButton(gui: InventoryGui) : StaticGuiElement {
        return StaticGuiElement('b',
            tippedArrow(Color.RED),
            {
                val player = it.event.whoClicked as Player
                gui.show(player)
                return@StaticGuiElement true
            },
            "§c§lBack"
        )
    }

    val backInHistory : GuiBackElement
        get() = GuiBackElement('b',
            tippedArrow(Color.RED),
            "§c§lBack"
        )

    val close: StaticGuiElement
        get() = StaticGuiElement('c',
            ItemStack(Material.BARRIER),
            {
                it.gui.destroy()
                return@StaticGuiElement true
            },
            "§c§lClose Menu"
        )

    val previousPage: GuiPageElement
        get() = GuiPageElement('1',
            Material.ARROW.item,
            GuiPageElement.PageAction.PREVIOUS,
            "§6§lPrevious",
            "§7Go to page %prevpage%"
        )

    val nextPage: GuiPageElement
        get() = GuiPageElement(
            '2',
            Material.ARROW.item,
            GuiPageElement.PageAction.NEXT,
            "§6§lNext",
            "§7Go to page %nextpage%"
        )

    fun createEditBannerButton(banner: ItemStack) : StaticGuiElement {
        return StaticGuiElement(
            'e',
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
        )
    }

    fun createGetShieldButton(banner: ItemStack) : StaticGuiElement {
        return StaticGuiElement(
            'S',
            Material.SHIELD.item,
            {
                val player = it.event.whoClicked as Player
                if (!Permissions.check(player, "bannerz.get.shield")) return@StaticGuiElement true
                player.inventory.addItem(NBT.getShield(banner))
                Sounds.play(player, Sound.ENTITY_VILLAGER_YES)
                return@StaticGuiElement true
            },
            "§6§lGet Shield",
            "§7Get this banner as a shield",
            "§7in your inventory",
            "§0 ",
            "§e§lCLICK §7to get"
        )
    }

    fun createSaveButton(banner: ItemStack) : StaticGuiElement {
        return StaticGuiElement('l',
            Material.BOOKSHELF.item,
            {
                val player = it.event.whoClicked as Player
                PlayerBanners.add(player.uniqueId, banner)
                player.sendMessage("§eBanner added to personal library!")
                Sounds.play(player, Sound.ENTITY_VILLAGER_YES)
                return@StaticGuiElement true
            },
            "§6§lSave",
            "§7Save this banner to",
            "§7your personal banner library",
            "§0 ",
            "§e§lCLICK §7to save"
        )
    }

    fun createGiveCommandButton(banner: ItemStack) : StaticGuiElement {
        return StaticGuiElement('g',
            Material.COMMAND_BLOCK.item,
            {
                val player = it.event.whoClicked as Player
                if (!Permissions.check(player, "bannerz.get.commandblock")) return@StaticGuiElement true

                val commandBlock: ItemStack = when (it.event.click) {
                    ClickType.LEFT -> NBT.getBannerCommandBlock(banner)
                    ClickType.RIGHT -> NBT.getShieldCommandBlock(banner)
                    else -> return@StaticGuiElement true
                }

                player.inventory.addItem(commandBlock)
                Sounds.play(player, Sound.ENTITY_VILLAGER_YES)
                return@StaticGuiElement true
            },
            "§6§lCommand Block",
            "§7Generates you a /give",
            "§7command for this banner",
            "§7or shield and gives you a",
            "§7command block for it",
            "§0 ",
            "§e§lL-CLICK §7for banner",
            "§e§lR-CLICK §7for shield"
        )
    }
}