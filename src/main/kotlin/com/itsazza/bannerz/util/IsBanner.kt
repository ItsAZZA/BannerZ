package com.itsazza.bannerz.util

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

fun isBanner(material: Material) = material.name.contains("BANNER", true)
fun isBanner(itemStack: ItemStack) = isBanner(itemStack.type)