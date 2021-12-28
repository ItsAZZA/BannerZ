package com.itsazza.bannerz.menus.playerlibrary.data

import org.bukkit.inventory.ItemStack
import org.bukkit.util.io.BukkitObjectOutputStream
import java.io.ByteArrayOutputStream
import java.util.*

fun serializeItemStack(itemStack: ItemStack) : String {
    val outputStream = ByteArrayOutputStream()
    val dataOutput = BukkitObjectOutputStream(outputStream)

    dataOutput.writeObject(itemStack)
    dataOutput.close()
    return Base64.getEncoder().encodeToString(outputStream.toByteArray())
}