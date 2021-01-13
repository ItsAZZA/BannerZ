package com.itsazza.bannerz.menus.library.data

import org.bukkit.inventory.ItemStack
import org.bukkit.util.io.BukkitObjectInputStream
import java.io.ByteArrayInputStream
import java.io.IOException
import java.util.*

fun deSerializeItemStack(encodedObject: String) : ItemStack? {
    return try {
        val byteArray = Base64.getDecoder().decode(encodedObject)
        val inputStream = ByteArrayInputStream(byteArray)
        val bukkitInputStream = BukkitObjectInputStream(inputStream)

        bukkitInputStream.readObject() as? ItemStack
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}