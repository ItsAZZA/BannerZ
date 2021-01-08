package com.itsazza.bannerz.menus.color

import com.itsazza.bannerz.BannerZPlugin
import com.itsazza.bannerz.util.dyeMaterial
import com.itsazza.bannerz.menus.closeButton
import com.itsazza.bannerz.menus.createBackButton
import com.itsazza.bannerz.menus.creator.CreatorMode
import com.itsazza.bannerz.menus.pattern.PatternMenu
import com.itsazza.bannerz.util.item
import de.themoep.inventorygui.GuiElementGroup
import de.themoep.inventorygui.InventoryGui
import de.themoep.inventorygui.StaticGuiElement
import org.bukkit.DyeColor
import org.bukkit.block.Block
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

object ColorMenu  {
    fun open(player: Player, banner: ItemStack, index: Int?, currentColor: DyeColor, creatorMode: CreatorMode, block: Block?) {
        create(player, banner, index, currentColor, creatorMode, block).show(player)
    }

    private fun create(player: Player, banner: ItemStack, index: Int?, currentColor: DyeColor, creatorMode: CreatorMode, block: Block?) : InventoryGui {
         val gui = InventoryGui(
             BannerZPlugin.instance,
             null,
             "Select a color",
             colorMenuTemplate
         )

        val group = GuiElementGroup('0')
        for (dye in dyes) {
            group.addElement(createColorSelectButton(player, banner, index, currentColor, dye, creatorMode, block))
        }

        gui.addElement(group)
        gui.addElement(createBackButton(PatternMenu.create(banner, currentColor, index, creatorMode, block)))
        gui.addElement(closeButton)
        return gui
    }

    private fun createColorSelectButton(player: Player, banner: ItemStack, index: Int?,
                                        currentColor: DyeColor, color: DyeColor,
                                        creatorMode: CreatorMode, block: Block?) : StaticGuiElement
    {
        val item = color.dyeMaterial.item

        if(currentColor == color) {
            val itemMeta = item.itemMeta
            itemMeta.addEnchant(Enchantment.LOOT_BONUS_MOBS, 1, true)
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
            item.itemMeta = itemMeta
        }

        return StaticGuiElement('@',
            item,
            1,
            {
                PatternMenu.open(player, banner, color, index, creatorMode, block)
                return@StaticGuiElement true
            },
            "§6§l${color.name.toLowerCase().capitalize()}",
            "§7Select ${color.name.toLowerCase()} as the",
            "§7pattern color",
            "§0 ",
            "§e§lCLICK §7to select"
            )
    }
}