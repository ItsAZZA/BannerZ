package com.itsazza.bannerz.menus

import com.itsazza.bannerz.util.item
import com.itsazza.bannerz.util.tippedArrow
import de.themoep.inventorygui.GuiBackElement
import de.themoep.inventorygui.GuiPageElement
import de.themoep.inventorygui.InventoryGui
import de.themoep.inventorygui.StaticGuiElement
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.entity.Player
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
}