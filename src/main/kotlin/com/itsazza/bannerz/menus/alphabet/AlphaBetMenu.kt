package com.itsazza.bannerz.menus.alphabet

import com.itsazza.bannerz.BannerZPlugin
import com.itsazza.bannerz.menus.closeButton
import com.itsazza.bannerz.menus.creator.color.dyes
import com.itsazza.bannerz.util.concreteMaterial
import com.itsazza.bannerz.util.dyeMaterial
import com.itsazza.bannerz.util.item
import de.themoep.inventorygui.GuiElementGroup
import de.themoep.inventorygui.InventoryGui
import de.themoep.inventorygui.StaticGuiElement
import org.bukkit.DyeColor
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag

object AlphaBetMenu  {
    fun open(player: Player, backgroundColor: DyeColor = DyeColor.WHITE, foregroundColor: DyeColor = DyeColor.BLACK) {
        create(backgroundColor, foregroundColor).show(player)
    }

    private fun create(backgroundColor: DyeColor, foregroundColor: DyeColor) : InventoryGui {
        val gui = InventoryGui(
            BannerZPlugin.instance,
            null,
            "Alphabet & Numerals",
            alphabetMenuTemplate
        )

        val group0 = GuiElementGroup('0')
        val group1 = GuiElementGroup('1')

        for(dye in dyes) {
            group0.addElement(createForegroundColorSelectButton(dye, backgroundColor, foregroundColor))
            group1.addElement(createBackgroundColorSelectButton(dye, backgroundColor, foregroundColor))
        }

        gui.addElement(
            StaticGuiElement(
                'p',
                alphabetBanners["A"]!!.build(foregroundColor, backgroundColor),
                "§6§lPreview"
            )
        )

        gui.addElement(group0)
        gui.addElement(group1)
        gui.addElement(closeButton)
        gui.setCloseAction { false }
        return gui
    }

    private fun createBackgroundColorSelectButton(color: DyeColor, backgroundColor: DyeColor, foregroundColor: DyeColor) : StaticGuiElement {
        val item = color.concreteMaterial.item

        if(color == backgroundColor) {
            val itemMeta = item.itemMeta
            itemMeta.addEnchant(Enchantment.LOOT_BONUS_MOBS, 1, true)
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
            item.itemMeta = itemMeta
        }

        return StaticGuiElement('@',
            item,
            {
                val player = it.event.whoClicked as Player
                open(player, color, foregroundColor)
                return@StaticGuiElement true
            },
            "§6§l${color.name.toLowerCase().capitalize()}",
            "§7Select ${color.name.toLowerCase()} as the",
            "§7background color",
            "§0 ",
            "§e§lCLICK §7to select"
        )
    }

    private fun createForegroundColorSelectButton(color: DyeColor, backgroundColor: DyeColor, foregroundColor: DyeColor) : StaticGuiElement {
        val item = color.dyeMaterial.item

        if(color == foregroundColor) {
            val itemMeta = item.itemMeta
            itemMeta.addEnchant(Enchantment.LOOT_BONUS_MOBS, 1, true)
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
            item.itemMeta = itemMeta
        }

        return StaticGuiElement('@',
            item,
            {
                val player = it.event.whoClicked as Player
                open(player, backgroundColor, color)
                return@StaticGuiElement true
            },
            "§6§l${color.name.toLowerCase().capitalize()}",
            "§7Select ${color.name.toLowerCase()} as the",
            "§7foreground/font color",
            "§0 ",
            "§e§lCLICK §7to select"
        )
    }
}