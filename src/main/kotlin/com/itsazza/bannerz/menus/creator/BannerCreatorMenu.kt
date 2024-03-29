package com.itsazza.bannerz.menus.creator

import com.itsazza.bannerz.BannerZPlugin
import com.itsazza.bannerz.builder.banner
import com.itsazza.bannerz.menus.Buttons
import com.itsazza.bannerz.menus.creator.color.BannerColorMenu
import com.itsazza.bannerz.menus.creator.pattern.PatternMenu
import com.itsazza.bannerz.menus.main.MainMenu
import com.itsazza.bannerz.util.*
import de.themoep.inventorygui.GuiElementGroup
import de.themoep.inventorygui.InventoryGui
import de.themoep.inventorygui.StaticGuiElement
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Banner
import org.bukkit.block.Block
import org.bukkit.block.banner.Pattern
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BannerMeta
import org.bukkit.inventory.meta.ItemMeta

object BannerCreatorMenu {
    fun open(player: Player, banner: ItemStack, creatorMode: CreatorMode = CreatorMode.CREATE, block: Block? = null) {
        create(banner, creatorMode, block).show(player)
    }

    fun create(banner: ItemStack, creatorMode: CreatorMode, block: Block?) : InventoryGui {
        val gui = InventoryGui(
            BannerZPlugin.instance,
            null,
            "Banner Creator",
            arrayOf(
                "    p    ",
                " 0000000 ",
                " 0000000 ",
                " 0000000 ",
                "    1    ",
                " blgcSs  "
            )
        )

        val bannerMeta = banner.itemMeta as BannerMeta
        banner.amount = 1
        val group = GuiElementGroup('0')
        group.addElement(createBannerBaseColorButton(banner, creatorMode, block))

        for((i, pattern) in bannerMeta.patterns.take(16).withIndex()) {
            group.addElement(createPatternButton(banner, creatorMode, block, pattern, i))
        }

        if (bannerMeta.numberOfPatterns() < 16) {
            group.addElement(createAddPatternButton(banner, creatorMode, block))
        }

        with(Buttons) {
            gui.addElements(
                createGiveCommandButton(banner),
                createSaveButton(banner),
                createBackButton(MainMenu.create()),
                createGetShieldButton(banner),
                group,
                createPreviewButton(banner),
                createGiveItemButton(banner),
                close
            )
        }

        if (creatorMode == CreatorMode.CHANGE && block != null) { gui.addElement(createSaveBlockButton(banner, block)) }
        gui.setCloseAction { false }
        return gui
    }

    private fun createBannerBaseColorButton(baseBanner: ItemStack, creatorMode: CreatorMode, block: Block?) : StaticGuiElement {
        return StaticGuiElement('@',
            baseBanner.type.item,
            {
                val player = it.event.whoClicked as Player
                BannerColorMenu.open(player, baseBanner, creatorMode, block)
                return@StaticGuiElement true
            },
            "§6§lBase Color",
            "§7Change the banner's base",
            "§7color from ${baseBanner.type.bannerColor.name.lowercase()}",
            "§0 ",
            "§e§lCLICK §7to change"
        )
    }

    private fun createPatternButton(banner: ItemStack, creatorMode: CreatorMode, block: Block?, bannerPattern: Pattern, index: Int) : StaticGuiElement {
        val item = banner(Material.WHITE_BANNER) { pattern(bannerPattern) }
        if (bannerPattern.color == DyeColor.WHITE) item.type = Material.BLACK_BANNER
        val patternName = bannerPattern.pattern.name.beautifyCapitalizeWords()

        return StaticGuiElement('@',
            item,
            {
                val player = it.event.whoClicked as Player
                when {
                    it.event.click.isShiftClick -> {
                        val bannerMeta = banner.itemMeta as BannerMeta
                        bannerMeta.removePattern(index)
                        banner.itemMeta = bannerMeta
                        Sounds.play(player, Sound.BLOCK_BEEHIVE_EXIT)
                        open(player, banner, creatorMode, block)
                        return@StaticGuiElement true
                    }
                    it.event.click.isRightClick -> {
                        val bannerMeta = banner.itemMeta as BannerMeta
                        if (index == 0) {
                            player.sendMessage("§cCannot move this pattern up!")
                            player.playSound(player.location, Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F)
                            return@StaticGuiElement true
                        }
                        val old = bannerMeta.getPattern(index)
                        val new = bannerMeta.getPattern(index - 1)
                        bannerMeta.setPattern(index - 1, old)
                        bannerMeta.setPattern(index, new)
                        banner.itemMeta = bannerMeta
                        open(player, banner, creatorMode, block)
                        return@StaticGuiElement true
                    }
                    it.event.isLeftClick -> {
                        PatternMenu.open(player, banner, bannerPattern.color, index, creatorMode, block)
                        return@StaticGuiElement true
                    }
                    else -> {
                        return@StaticGuiElement true
                    }
                }
            },
            "§6§l${patternName}",
            "§7A ${bannerPattern.color.name.lowercase()} $patternName",
            "§7banner pattern",
            "§0 ",
            "§e§lL-CLICK §7to edit",
            "§e§lR-CLICK §7to move up",
            "§e§lSHIFT-CLICK §7to remove"
            )
    }

    private fun createAddPatternButton(banner: ItemStack, creatorMode: CreatorMode, block: Block?) : StaticGuiElement {
        return StaticGuiElement(
            '@',
            Material.ACACIA_BUTTON.item,
            {
                val player = it.event.whoClicked as Player
                PatternMenu.open(player, banner, creatorMode = creatorMode, block = block)
                return@StaticGuiElement true
            },
            "§6§lAdd Pattern",
            "§7Add a new pattern to this",
            "§7banner",
            "§0 ",
            "§e§lCLICK §7to select"
        )
    }

    private fun createPreviewButton(banner: ItemStack): StaticGuiElement {
        return StaticGuiElement(
            'p',
            banner.mutateMeta<ItemMeta> {
                it.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS)
            },
            {
                return@StaticGuiElement true
            },
            "§6§lPreview"
        )
    }

    private fun createGiveItemButton(banner: ItemStack) : StaticGuiElement {
        return StaticGuiElement(
            's',
            Material.CHEST.item,
            { click ->
                val player = click.event.whoClicked as Player
                if (!checkBanner(banner, player)) return@StaticGuiElement true
                player.inventory.addItem(banner)
                player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F)
                return@StaticGuiElement true
            },
            "§6§lGet Banner",
            "§7Gives you the created",
            "§7banner item",
            "§0 ",
            "§e§lCLICK §7to select"
        )
    }

    private fun createSaveBlockButton(banner: ItemStack, block: Block) : StaticGuiElement {
        return StaticGuiElement('1',
            Material.GREEN_DYE.item,
            {
                val blockBanner = block.state as? Banner ?: throw IllegalArgumentException()
                val bannerMeta = banner.itemMeta as BannerMeta
                val patterns = bannerMeta.patterns

                blockBanner.baseColor = banner.type.bannerColor
                blockBanner.patterns = patterns
                blockBanner.update()
                it.gui.destroy()
                return@StaticGuiElement true
            },
            "§6§lUpdate Banner",
            "§7Sets the banner block",
            "§7as the edited banner",
            "§0 ",
            "§e§lCLICK §7to set"
        )
    }
}