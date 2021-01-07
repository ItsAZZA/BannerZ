package com.itsazza.bannerz.menus

import de.themoep.inventorygui.GuiElement
import de.themoep.inventorygui.StaticGuiElement
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

val closeButton = StaticGuiElement('c', ItemStack(Material.BARRIER), GuiElement.Action { it.gui.destroy(); return@Action true }, "§c§lClose")