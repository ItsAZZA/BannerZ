package com.itsazza.bannerz.menus.library.data

import org.bukkit.inventory.ItemStack
import java.util.*

fun serializeItemStack(itemStack: ItemStack) : String? {
    return Base64.getEncoder().encodeToString(itemStack.serializeAsBytes())
}