package com.itsazza.bannerz.menus

import com.itsazza.bannerz.util.item
import de.themoep.inventorygui.InventoryGui
import de.themoep.inventorygui.StaticGuiElement
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.meta.PotionMeta

fun createBackButton(gui: InventoryGui) : StaticGuiElement {
    val arrow = Material.TIPPED_ARROW.item
    val arrowMeta = arrow.itemMeta as PotionMeta
    arrowMeta.color = Color.RED
    arrowMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS)
    arrow.itemMeta = arrowMeta

    return StaticGuiElement('b',
        arrow,
        1,
        {
            val player = it.event.whoClicked as Player
            gui.show(player)
            return@StaticGuiElement true
        },
        "§c§lBack"
    )
}