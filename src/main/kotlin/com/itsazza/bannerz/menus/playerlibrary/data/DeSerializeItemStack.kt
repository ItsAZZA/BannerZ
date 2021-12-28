package com.itsazza.bannerz.menus.playerlibrary.data

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.util.io.BukkitObjectInputStream
import java.io.ByteArrayInputStream
import java.util.*

fun deSerializeItemStack(string: String): ItemStack {
        val inputStream = ByteArrayInputStream(Base64.getDecoder().decode(string))
        val dataInput = BukkitObjectInputStream(inputStream)
        val item = dataInput.readObject() as? ItemStack
        dataInput.close()
        return item ?: ItemStack(Material.WHITE_BANNER)
}