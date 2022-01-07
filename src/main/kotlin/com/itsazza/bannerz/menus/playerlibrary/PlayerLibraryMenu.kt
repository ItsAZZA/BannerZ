package com.itsazza.bannerz.menus.playerlibrary

import com.itsazza.bannerz.BannerZPlugin
import com.itsazza.bannerz.menus.Buttons
import com.itsazza.bannerz.menus.Buttons.close
import com.itsazza.bannerz.menus.Buttons.createBackButton
import com.itsazza.bannerz.menus.Buttons.nextPage
import com.itsazza.bannerz.menus.Buttons.previousPage
import com.itsazza.bannerz.menus.creator.BannerCreatorMenu
import com.itsazza.bannerz.menus.main.MainMenu
import com.itsazza.bannerz.menus.playerlibrary.data.PlayerBanners
import com.itsazza.bannerz.util.*
import com.itsazza.bannerz.util.storage.Storage
import de.themoep.inventorygui.GuiElementGroup
import de.themoep.inventorygui.GuiStateElement
import de.themoep.inventorygui.InventoryGui
import de.themoep.inventorygui.StaticGuiElement
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.collections.HashMap

object PlayerLibraryMenu {
    private val plugin = BannerZPlugin.instance

    fun open(ownerUUID: UUID, viewer: Player) {
        val banners = PlayerBanners.get(ownerUUID)

        if (banners.isNullOrEmpty()) {
            viewer.sendMessage("§cNo saved banners found!")
            return
        }

        if (ownerUUID == viewer.uniqueId) {
            create(ownerUUID, banners).show(viewer)
        } else {
            if (Storage.getVisitorStatus(ownerUUID.toString()) || viewer.hasPermission("bannerz.admin")) {
                createSpectateMenu(ownerUUID, banners).show(viewer)
            } else {
                viewer.sendMessage("§cThis player's banners are not publicly visible.")
                Sounds.play(viewer, Sound.ENTITY_VILLAGER_NO)
            }
        }
    }

    fun create(ownerUUID: UUID, banners: HashMap<Int, ItemStack>): InventoryGui {
        val gui = InventoryGui(
            plugin,
            null,
            "Your Banner Library",
            libraryMenuTemplate
        )

        val group = GuiElementGroup('0')

        for ((index, banner) in banners) {
            group.addElement(createBannerLibraryButton(banner, index))
        }

        val toggleButton = toggleVisitorsButton
        toggleButton.setState(Storage.getVisitorStatus(ownerUUID.toString()).toString())

        gui.addElements(
            group,
            toggleButton,
            previousPage,
            nextPage,
            createBackButton(MainMenu.create()),
            close
        )

        gui.setCloseAction { false }
        return gui
    }

    private fun createSpectateMenu(owner: UUID, banners: HashMap<Int, ItemStack>): InventoryGui {
        val playerName = Bukkit.getOfflinePlayer(owner).name ?: "Unknown"
        val gui = InventoryGui(
            plugin,
            null,
            "${playerName}'s banners",
            libraryMenuTemplate
        )

        val group = GuiElementGroup('0')
        for (banner in banners) {
            group.addElement(createBannerSpectateLibraryButton(playerName, banner.value))
        }

        gui.addElements(
            group,
            previousPage,
            nextPage,
            createBackButton(MainMenu.create()),
            close
        )

        gui.setCloseAction { false }
        return gui
    }

    private fun createBannerLibraryButton(banner: ItemStack, index: Int): StaticGuiElement {
        return StaticGuiElement(
            '@',
            banner,
            {
                val player = it.event.whoClicked as Player
                if (it.event.isLeftClick) {
                    if (!checkBanner(banner, player)) return@StaticGuiElement true
                    player.inventory.addItem(banner)
                    Sounds.play(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP)
                    return@StaticGuiElement true
                } else {
                    createBannerGetMenu(banner, index).show(player)
                    return@StaticGuiElement true
                }
            },
            if (banner.itemMeta!!.hasDisplayName()) "§6§l${banner.itemMeta!!.displayName}" else "§6§lBanner",
            "§0 ",
            "§e§lL-CLICK §7to get",
            "§e§lR-CLICK §7for more options"
        )
    }

    private fun createBannerSpectateLibraryButton(ownerName: String, banner: ItemStack): StaticGuiElement {
        return StaticGuiElement(
            '@',
            banner,
            {
                val player = it.event.whoClicked as Player
                when {
                    it.event.isShiftClick -> {
                        val commandBlock = NBT.getBannerCommandBlock(banner)
                        player.inventory.addItem(commandBlock)
                        return@StaticGuiElement true
                    }
                    it.event.isLeftClick -> {
                        if (!checkBanner(banner, player)) return@StaticGuiElement true
                        player.inventory.addItem(banner)
                        Sounds.play(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP)
                        return@StaticGuiElement true
                    }
                    it.event.isRightClick -> {
                        BannerCreatorMenu.open(player, banner)
                        return@StaticGuiElement true
                    }
                }
                return@StaticGuiElement true
            },
            if (banner.itemMeta!!.hasDisplayName()) "§6§l${banner.itemMeta!!.displayName}" else "§6§lBanner",
            "§7Banner in ${ownerName}'s",
            "§7personal banner library",
            "§0 ",
            "§e§lL-CLICK §7to get",
            "§e§lR-CLICK §7to open editor",
            "§e§lSHIFT-CLICK §7to get command block"
        )
    }

    private val toggleVisitorsButton = GuiStateElement(
        't',
        GuiStateElement.State(
            {
                val player = it.event.whoClicked as Player
                val uuid = player.uniqueId.toString()
                Storage.setVisitorStatus(uuid, true)
            },
            "true",
            Material.LIME_DYE.item,
            "§6§lToggle visibility",
            "§7Toggles whether players",
            "§7are able to check your saved",
            "§7banners in this library",
            "§0",
            "§7Visibility: §atrue",
            "§0 ",
            "§e§lCLICK §7to toggle"
        ),
        GuiStateElement.State(
            {
                val player = it.event.whoClicked as Player
                val uuid = player.uniqueId.toString()
                Storage.setVisitorStatus(uuid, false)
            },
            "false",
            Material.GRAY_DYE.item,
            "§6§lToggle visibility",
            "§7Toggles whether players",
            "§7are able to check your saved",
            "§7banners in this library",
            "§0",
            "§7Visibility: §cfalse",
            "§0 ",
            "§e§lCLICK §7to toggle"
        )
    )

    private fun createBannerGetMenu(banner: ItemStack, index: Int): InventoryGui {
        val gui = InventoryGui(
            plugin,
            null,
            "Banner",
            arrayOf(
                "    p    ",
                "   egr   ",
                "    b    "
            )
        )

        gui.addElement(BannerCreatorMenu.createGiveCommandButton(banner))
        gui.addElement(Buttons.createEditBannerButton(banner))
        gui.addElement(
            StaticGuiElement(
                'r',
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
            )
        )

        gui.addElement(Buttons.backInHistory)
        gui.setCloseAction { false }
        return gui
    }
}