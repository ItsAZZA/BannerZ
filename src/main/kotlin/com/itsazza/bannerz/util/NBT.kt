package com.itsazza.bannerz.util

import de.tr7zw.changeme.nbtapi.NBTCompound
import de.tr7zw.changeme.nbtapi.NBTItem
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object NBT {
    fun getBannerCommandBlock(banner: ItemStack): ItemStack {
        val bannerPatternCompound = NBTItem(banner).getCompound("BlockEntityTag")
        return getCommandBlockWithCommand("minecraft:give @p minecraft:${banner.type.name.lowercase()}{BlockEntityTag:$bannerPatternCompound} 1")
    }

    fun getShield(banner: ItemStack): ItemStack {
        val compound = getShieldTag(banner)
        val shieldItem = NBTItem(Material.SHIELD.item)
        val tag = shieldItem.getOrCreateCompound("BlockEntityTag")
        tag.mergeCompound(compound)
        shieldItem.mergeCompound(tag)
        return shieldItem.item
    }

    fun getShieldCommandBlock(banner: ItemStack) : ItemStack {
        return getCommandBlockWithCommand("minecraft:give @p minecraft:shield{BlockEntityTag:${getShieldTag(banner)}} 1")
    }

    private fun getShieldTag(banner: ItemStack): NBTCompound {
        val item = NBTItem(banner)
        val bannerPatternCompound = item.getOrCreateCompound("BlockEntityTag")
        bannerPatternCompound.setInteger("Base", banner.type.bannerColor.toInt())
        return bannerPatternCompound
    }

    private fun getCommandBlockWithCommand(command: String): ItemStack {
        val commandBlockItem = NBTItem(Material.COMMAND_BLOCK.item)
        val tag = commandBlockItem.getOrCreateCompound("BlockEntityTag")
        tag.setString("Command", command)
        commandBlockItem.mergeCompound(tag)
        return commandBlockItem.item
    }
}