package com.itsazza.bannerz.util

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.Tag.REGISTRY_BLOCKS

fun isBanner(material: Material) = Bukkit.getTag(REGISTRY_BLOCKS, NamespacedKey.minecraft("banners"), Material::class.java).isTagged(material)