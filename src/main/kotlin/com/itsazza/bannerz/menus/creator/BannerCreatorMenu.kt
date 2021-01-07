package com.itsazza.bannerz.menus.creator

import com.google.gson.*
import com.itsazza.bannerz.BannerZPlugin
import com.itsazza.bannerz.builder.banner
import com.itsazza.bannerz.menus.closeButton
import com.itsazza.bannerz.menus.color.BannerColorMenu
import com.itsazza.bannerz.menus.createBackButton
import com.itsazza.bannerz.menus.main.MainMenu
import com.itsazza.bannerz.menus.pattern.PatternMenu
import com.itsazza.bannerz.util.nms.NMS
import com.itsazza.bannerz.util.bannerColor
import com.itsazza.bannerz.util.item
import de.themoep.inventorygui.GuiElementGroup
import de.themoep.inventorygui.InventoryGui
import de.themoep.inventorygui.StaticGuiElement
import net.minecraft.server.v1_16_R3.*
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.banner.Pattern
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BannerMeta

object BannerCreatorMenu {
    fun open(player: Player, banner: ItemStack, creatorMode: CreatorMode = CreatorMode.CREATE) {
        create(banner, creatorMode).show(player)
    }

    fun create(banner: ItemStack, creatorMode: CreatorMode) : InventoryGui {
        val gui = InventoryGui(
            BannerZPlugin.instance,
            null,
            "Banner Creator",
            creatorMenuTemplate
        )

        val bannerMeta = banner.itemMeta as BannerMeta
        banner.amount = 1
        val group = GuiElementGroup('0')
        group.addElement(createBannerBaseColorButton(banner, creatorMode))

        for((i, pattern) in bannerMeta.patterns.take(16).withIndex()) {
            group.addElement(createPatternButton(banner, creatorMode, pattern, i))
        }

        if (bannerMeta.numberOfPatterns() < 16) {
            group.addElement(createAddPatternButton(banner))
        }

        gui.addElement(group)
        gui.addElement(createPreviewButton(banner))
        gui.addElement(createGiveItemButton(banner))
        gui.addElement(createGiveCommandButton(banner))
        gui.addElement(createSaveButton())
        gui.addElement(createBackButton(MainMenu.create()))
        gui.closeAction = InventoryGui.CloseAction { false }
        gui.addElement(closeButton)
        return gui
    }

    private fun createBannerBaseColorButton(baseBanner: ItemStack, creatorMode: CreatorMode) : StaticGuiElement {
        return StaticGuiElement('@',
            baseBanner.type.item,
            1,
            {
                val player = it.event.whoClicked as Player
                BannerColorMenu.openMenu(player, baseBanner, creatorMode)
                return@StaticGuiElement true
            },
            "§6§lBase Color",
            "§7Change the banner's base",
            "§7color from ${baseBanner.type.bannerColor.name.toLowerCase()}",
            "§0 ",
            "§e§lCLICK §7to change"
        )
    }

    private fun createPatternButton(banner: ItemStack, creatorMode: CreatorMode, bannerPattern: Pattern, index: Int) : StaticGuiElement {
        val item = banner(Material.WHITE_BANNER) { pattern(bannerPattern) }
        if(bannerPattern.color == DyeColor.WHITE) {
            item.type = Material.BLACK_BANNER
        }

        val patternName = bannerPattern.pattern.name.toLowerCase().split("_").joinToString(" "){ it.capitalize() }

        return StaticGuiElement('@',
            item,
            1,
            {
                val player = it.event.whoClicked as Player
                when {
                    it.event.click.isShiftClick -> {
                        val bannerMeta = banner.itemMeta as BannerMeta
                        bannerMeta.removePattern(index)
                        banner.itemMeta = bannerMeta
                        open(player, banner, creatorMode)
                        return@StaticGuiElement true
                    }
                    it.event.click.isRightClick -> {
                        val bannerMeta = banner.itemMeta as BannerMeta
                        if(index == 0) {
                            player.sendMessage("§cCannot move this pattern up!")
                            player.playSound(player.location, Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F)
                            return@StaticGuiElement true
                        }
                        bannerMeta.setPattern(index - 1, bannerMeta.getPattern(index))
                        bannerMeta.setPattern(index, bannerMeta.getPattern(index - 1))
                        banner.itemMeta = bannerMeta
                        open(player, banner, creatorMode)
                        return@StaticGuiElement true
                    }
                    it.event.isLeftClick -> {
                        PatternMenu.openMenu(player, banner, bannerPattern.color, index, creatorMode)
                        return@StaticGuiElement true
                    }
                    else -> {
                        return@StaticGuiElement true
                    }
                }
            },
            "§6§l${patternName}",
            "§7A ${bannerPattern.color.name.toLowerCase()} $patternName",
            "§7banner pattern at index ${index + 1}",
            "§0 ",
            "§e§lL-CLICK §7to edit",
            "§e§lR-CLICK §7to move up",
            "§e§lSHIFT-CLICK §7to remove"
            )
    }

    private fun createAddPatternButton(banner: ItemStack) : StaticGuiElement {
        return StaticGuiElement(
            '@',
            Material.CRIMSON_BUTTON.item,
            1,
            {
                val player = it.event.whoClicked as Player
                PatternMenu.openMenu(player, banner, creatorMode = CreatorMode.EDIT)
                return@StaticGuiElement true
            },
            "§6§lAdd Pattern",
            "§7Add a new pattern to this",
            "§7banner",
            "§0 ",
            "§e§lCLICK §7to select"
        )
    }

    private fun createPreviewButton(banner: ItemStack) : StaticGuiElement {
        val itemMeta = banner.itemMeta
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS)
        banner.itemMeta = itemMeta

        return StaticGuiElement('p',
            banner,
            1,
            {
                return@StaticGuiElement true
            },
            "§6§lPreview"
            )
    }

    private fun createGiveItemButton(banner: ItemStack) : StaticGuiElement {
        return StaticGuiElement('s',
            Material.CHEST.item,
            {
                val player = it.event.whoClicked as Player
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

    private fun createSaveButton() : StaticGuiElement {
        return StaticGuiElement('l',
            Material.BOOKSHELF.item,
            "§6§lSave",
            "§7Save this banner to",
            "§7your personal banner library",
            "§0 ",
            "§e§lCLICK §7to save"
            )
    }

    private fun createGiveCommandButton(banner: ItemStack) : StaticGuiElement {
        return StaticGuiElement('g',
            Material.COMMAND_BLOCK.item,
            {
                val json = NMS.getBannerPatternJson(banner)
                val commandString = "minecraft:give @p minecraft:${banner.type.name.toLowerCase()}$json 1"
                val tag = NBTTagCompound()
                val command = NBTTagCompound()
                command.setString("Command", commandString)
                tag.set("BlockEntityTag", command)
                val nmsCommandBlock = ItemStack(Items.ez)
                nmsCommandBlock.tag = tag

                val player = it.event.whoClicked as Player
                player.inventory.addItem(CraftItemStack.asCraftMirror(nmsCommandBlock))
                player.playSound(player.location, Sound.ENTITY_VILLAGER_YES, 1.0F, 1.0F)
                return@StaticGuiElement true
            },
            "§6§lCommand Block",
            "§7Generates you a /give",
            "§7command for this banner",
            "§7and gives you a command",
            "§7block for it",
            "§0 ",
            "§e§lCLICK §7to get"
            )
    }
}