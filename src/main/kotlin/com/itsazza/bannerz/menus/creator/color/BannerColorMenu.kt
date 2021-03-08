package com.itsazza.bannerz.menus.creator.color

import com.itsazza.bannerz.BannerZPlugin
import com.itsazza.bannerz.menus.Buttons.close
import com.itsazza.bannerz.menus.Buttons.createBackButton
import com.itsazza.bannerz.menus.creator.BannerCreatorMenu
import com.itsazza.bannerz.menus.creator.CreatorMode
import com.itsazza.bannerz.util.bannerMaterial
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

object BannerColorMenu {
    fun open(player: Player, banner: ItemStack, creatorMode: CreatorMode, block: Block?) {
        create(banner, creatorMode, block).show(player)
    }

    private fun create(banner: ItemStack, creatorMode: CreatorMode, block: Block?) : InventoryGui {
        val gui = InventoryGui(
            BannerZPlugin.instance,
            null,
            "Select a color",
            colorMenuTemplate
        )

        val group = GuiElementGroup('0')
        for (dye in dyes) {
            group.addElement(createColorSelectButton(banner, dye, creatorMode, block))
        }

        gui.addElements(
            group,
            createBackButton(BannerCreatorMenu.create(banner, creatorMode, block)),
            close
        )

        return gui
    }

    private fun createColorSelectButton(banner: ItemStack, color: DyeColor, creatorMode: CreatorMode, block: Block?) : StaticGuiElement {
        val item = color.bannerMaterial.item
        val colorName = color.name.replace("_", " ").toLowerCase()

        if(banner.type == color.bannerMaterial) {
            val itemMeta = item.itemMeta
            itemMeta.addEnchant(Enchantment.LOOT_BONUS_MOBS, 1, true)
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
            item.itemMeta = itemMeta
        }

        return StaticGuiElement('@',
            item,
            {
                val player = it.event.whoClicked as Player
                banner.type = color.bannerMaterial
                BannerCreatorMenu.open(player, banner, creatorMode, block)
                return@StaticGuiElement true
            },
            "§6§l${colorName.capitalize()}",
            "§7Select $colorName as the",
            "§7pattern color",
            "§0 ",
            "§e§lCLICK §7to select"
        )
    }
}