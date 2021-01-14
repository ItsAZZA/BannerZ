package com.itsazza.bannerz.menus.library.data

import org.bukkit.inventory.ItemStack
import org.bukkit.util.io.BukkitObjectOutputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*

fun serializeItemStack(itemStack: ItemStack) : String? {
    return Base64.getEncoder().encodeToString(itemStack.serializeAsBytes())
}