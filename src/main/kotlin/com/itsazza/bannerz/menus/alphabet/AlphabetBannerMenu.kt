package com.itsazza.bannerz.menus.alphabet

import com.itsazza.bannerz.BannerZPlugin
import com.itsazza.bannerz.menus.Buttons
import com.itsazza.bannerz.menus.Buttons.backInHistory
import com.itsazza.bannerz.menus.Buttons.close
import com.itsazza.bannerz.menus.Buttons.nextPage
import com.itsazza.bannerz.menus.Buttons.previousPage
import com.itsazza.bannerz.menus.creator.BannerCreatorMenu
import com.itsazza.bannerz.menus.creator.BannerCreatorMenu.createGiveCommandButton
import com.itsazza.bannerz.menus.creator.BannerCreatorMenu.createSaveButton
import com.itsazza.bannerz.util.Sounds
import com.itsazza.bannerz.util.checkSurvivalCrafting
import com.itsazza.bannerz.util.item
import de.themoep.inventorygui.GuiElementGroup
import de.themoep.inventorygui.InventoryGui
import de.themoep.inventorygui.StaticGuiElement
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object AlphabetBannerMenu {
    private val plugin = BannerZPlugin.instance

    fun open(player: Player, foregroundColor: DyeColor, backgroundColor: DyeColor) {
        create(foregroundColor, backgroundColor).show(player)
    }

    fun create(foregroundColor: DyeColor, backgroundColor: DyeColor) : InventoryGui {
        val gui = InventoryGui(
            this.plugin,
            null,
            "Alphabet Banners",
            alphabetBannerMenuTemplate
        )

        val group = GuiElementGroup('0')
        for (banner in alphabetBanners) {
            val button = createGetAlphabetBannerButton(banner.value, banner.key, foregroundColor, backgroundColor)
            group.addElement(button)
        }

        gui.addElements(
            group,
            nextPage,
            previousPage,
            backInHistory,
            close
        )

        gui.setCloseAction { false }
        return gui
    }

    private fun createGetAlphabetBannerButton(
        bannerTemplate: BannerTemplate,
        bannerName: String,
        foregroundColor: DyeColor,
        backgroundColor: DyeColor) : StaticGuiElement {
        val item = bannerTemplate.build(foregroundColor, backgroundColor)
        return StaticGuiElement('@',
            item,
            {
                val player = it.event.whoClicked as Player
                if (it.event.isLeftClick) {
                    if (!checkSurvivalCrafting(item, player)) return@StaticGuiElement true
                    player.inventory.addItem(item)
                    Sounds.play(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP)
                    return@StaticGuiElement true
                } else {
                    createBannerGetMenu(item).show(player)
                    return@StaticGuiElement true
                }
            },
            "§6§l$bannerName",
            "§7Get this $bannerName banner",
            "§0 ",
            "§e§lL-CLICK §7to get item",
            "§e§lR-CLICK §7for more options"
            )
    }

    private fun createBannerGetMenu(banner: ItemStack) : InventoryGui {
        val gui = InventoryGui(
            this.plugin,
            null,
            "Get Alphabet Banner",
            arrayOf(
                "         ",
                "   lge   ",
                "    b    ",
                "         "
            )
        )

        gui.addElements(
            createGiveCommandButton(banner),
            createSaveButton(banner),
            Buttons.createEditBannerButton(banner),
            backInHistory
        )

        gui.setCloseAction { false }
        return gui
    }
}