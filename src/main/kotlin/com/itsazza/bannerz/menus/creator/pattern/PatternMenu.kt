package com.itsazza.bannerz.menus.creator.pattern

import com.itsazza.bannerz.BannerZPlugin
import com.itsazza.bannerz.builder.banner
import com.itsazza.bannerz.menus.Buttons.close
import com.itsazza.bannerz.menus.Buttons.createBackButton
import com.itsazza.bannerz.menus.Buttons.nextPage
import com.itsazza.bannerz.menus.Buttons.previousPage
import com.itsazza.bannerz.menus.creator.color.ColorMenu
import com.itsazza.bannerz.menus.creator.BannerCreatorMenu
import com.itsazza.bannerz.menus.creator.CreatorMode
import com.itsazza.bannerz.util.dyeMaterial
import com.itsazza.bannerz.util.item
import de.themoep.inventorygui.GuiElementGroup
import de.themoep.inventorygui.InventoryGui
import de.themoep.inventorygui.StaticGuiElement
import org.bukkit.Color
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.banner.Pattern
import org.bukkit.block.banner.PatternType
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BannerMeta
import org.bukkit.inventory.meta.PotionMeta

object PatternMenu {
    fun open(player: Player, banner: ItemStack, color: DyeColor = DyeColor.RED, index: Int? = null, creatorMode: CreatorMode, block: Block?) {
        create(banner, color, index, creatorMode, block).show(player)
    }

    fun create(banner: ItemStack, color: DyeColor, index: Int?, creatorMode: CreatorMode, block: Block?) : InventoryGui {
        val gui = InventoryGui(
            BannerZPlugin.instance,
            null,
            "Pattern Editor",
            patternMenuTemplate
        )

        val group = GuiElementGroup('0')
        PatternType.values().drop(1).forEach {
            group.addElement(createPatternButton(banner, it, color, index, creatorMode, block))
        }

        gui.addElement(group)
        gui.addElement(nextPage)
        gui.addElement(previousPage)
        gui.addElement(createPreviewButton(banner))
        gui.addElement(createChangeColorButton(banner, index, color, creatorMode, block))
        gui.addElement(createBackButton(BannerCreatorMenu.create(banner, creatorMode, block)))
        gui.addElement(createGoBackAndSaveButton(banner, color, index, creatorMode, block))
        gui.addElement(close)
        gui.setCloseAction { false }
        return gui
    }

    private fun createPatternButton(banner: ItemStack, patternType: PatternType, color: DyeColor, index: Int?, creatorMode: CreatorMode, block: Block?) : StaticGuiElement {
        val item = banner(Material.WHITE_BANNER) {pattern(color, patternType)}
        if (color == DyeColor.WHITE) {
            item.type = Material.BLACK_BANNER
        }

        val bannerMeta = banner.itemMeta as BannerMeta
        val patternName = patternType.name.toLowerCase().split("_").joinToString(" ") { it.capitalize() }
        val pattern = Pattern(color, patternType)

        return StaticGuiElement('@',
            item,
            1,
            {
                val player = it.event.whoClicked as Player
                if (index == null) {
                    bannerMeta.addPattern(pattern)
                    banner.itemMeta = bannerMeta
                    BannerCreatorMenu.open(player, banner, creatorMode, block)
                } else {
                    bannerMeta.setPattern(index, pattern)
                    banner.itemMeta = bannerMeta
                    BannerCreatorMenu.open(player, banner, creatorMode, block)
                }
                return@StaticGuiElement true
            },
            "§6§l$patternName",
            "§7Adds a $patternName pattern",
            "§7in ${color.name.toLowerCase()} color",
            "§0 ",
            "§e§lCLICK §7to add"
            )
    }

    private fun createGoBackAndSaveButton(banner: ItemStack, color: DyeColor, index: Int?, creatorMode: CreatorMode, block: Block?) : StaticGuiElement {
        val item = Material.TIPPED_ARROW.item
        val arrowMeta = item.itemMeta as PotionMeta
        arrowMeta.color = Color.LIME
        arrowMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS)
        item.itemMeta = arrowMeta


        return StaticGuiElement('s',
            item,
            {
                val player = it.event.whoClicked as Player
                if (index == null) {
                    BannerCreatorMenu.open(player, banner, creatorMode, block)
                } else {
                    val bannerMeta = banner.itemMeta as BannerMeta
                    val pattern = Pattern(color, bannerMeta.getPattern(index).pattern)
                    bannerMeta.setPattern(index, pattern)
                    banner.itemMeta = bannerMeta
                    BannerCreatorMenu.open(player, banner, creatorMode, block)
                }
                return@StaticGuiElement true
            },
            "§6§lSave & Go Back"
            )
    }

    private fun createPreviewButton(banner: ItemStack) : StaticGuiElement {
        val itemMeta = banner.itemMeta
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS)
        banner.itemMeta = itemMeta

        return StaticGuiElement('p',
            banner,
            {
                return@StaticGuiElement true
            },
            "§6§lPreview"
        )
    }

    private fun createChangeColorButton(banner: ItemStack, index: Int?, color: DyeColor, creatorMode: CreatorMode, block: Block?) : StaticGuiElement {
        return StaticGuiElement('d',
            color.dyeMaterial.item,
            {
                val player = it.event.whoClicked as Player
                ColorMenu.open(player, banner, index, color, creatorMode, block)
                return@StaticGuiElement true
            },
            "§6§lPattern Color",
            "§7Change the pattern color",
            "§7from ${color.name.toLowerCase()}",
            "§0 ",
            "§e§lCLICK §7to change"
            )
    }
}