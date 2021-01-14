package com.itsazza.bannerz.nms

import com.google.common.base.Throwables
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.itsazza.bannerz.util.item
import net.minecraft.server.v1_16_R3.Items
import net.minecraft.server.v1_16_R3.NBTTagCompound
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack

object NMS {
    fun getBannerCommandBlock(banner: ItemStack) : ItemStack {
        val json = getBannerPatternJson(banner)
        val tag = NBTTagCompound()
        val command = NBTTagCompound()
        val commandString = "minecraft:give @p minecraft:${banner.type.name.toLowerCase()}$json 1"
        command.setString("Command", commandString)
        tag.set("BlockEntityTag", command)

        val nmsCommandBlock = CraftItemStack.asNMSCopy(Material.COMMAND_BLOCK.item)
        nmsCommandBlock.tag = tag
        return CraftItemStack.asCraftMirror(nmsCommandBlock)
    }

    private fun getBannerPatternJson(banner: ItemStack): String {
        val craftBanner = CraftItemStack.asNMSCopy(banner)
        val tag = craftBanner.tag ?: NBTTagCompound()
        return tag.toString()
    }
}