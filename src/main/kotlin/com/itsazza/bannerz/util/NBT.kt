package com.itsazza.bannerz.util

import de.tr7zw.changeme.nbtapi.NBTItem
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object NBT {
    fun getBannerCommandBlock(banner: ItemStack): ItemStack {
        val item = NBTItem(banner)
        val bannerPatternCompound = item.compound.toString()
        val commandString = "minecraft:give @p minecraft:${banner.type.name.lowercase()}$bannerPatternCompound 1"
        val commandBlockItem = NBTItem(Material.COMMAND_BLOCK.item)

        val tag = commandBlockItem.getOrCreateCompound("BlockEntityTag")
        tag.setString("Command", commandString)
        commandBlockItem.mergeCompound(tag)
        return commandBlockItem.item
    }
}