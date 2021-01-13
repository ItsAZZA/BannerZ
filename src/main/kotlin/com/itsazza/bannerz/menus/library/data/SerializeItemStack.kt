package com.itsazza.bannerz.menus.library.data

import org.bukkit.inventory.ItemStack
import org.bukkit.util.io.BukkitObjectOutputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*

fun serializeItemStack(itemStack: ItemStack) : String? {
    return try {
        val io = ByteArrayOutputStream()
        val os = BukkitObjectOutputStream(io)
        os.writeObject(itemStack)
        os.flush()

        Base64.getEncoder().encodeToString(io.toByteArray())
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}