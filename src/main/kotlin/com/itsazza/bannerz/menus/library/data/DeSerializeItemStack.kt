package com.itsazza.bannerz.menus.library.data

import org.bukkit.inventory.ItemStack
import java.util.*

fun deSerializeItemStack(string: String): ItemStack {
        return ItemStack.deserializeBytes(Base64.getDecoder().decode(string))
}