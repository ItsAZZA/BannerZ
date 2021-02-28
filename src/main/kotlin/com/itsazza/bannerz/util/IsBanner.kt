package com.itsazza.bannerz.util

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.Tag.REGISTRY_BLOCKS
import org.bukkit.inventory.ItemStack

fun isBanner(material: Material) = Bukkit.getTag(REGISTRY_BLOCKS, NamespacedKey.minecraft("banners"), Material::class.java).isTagged(material)
fun isBanner(itemStack: ItemStack) = isBanner(itemStack.type)