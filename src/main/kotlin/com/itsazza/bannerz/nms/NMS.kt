package com.itsazza.bannerz.nms

import com.google.common.base.Throwables
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import net.minecraft.server.v1_16_R3.NBTTagCompound
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack

object NMS {
    private val MC_VERSION =
        Bukkit.getServer().javaClass.getPackage().name.replace(".", ",").split(",").toTypedArray()[3]

    private val CODE_PATH_NMS = "net.minecraft.server.$MC_VERSION."
    private val CODE_PATH_BUKKIT = "org.bukkit.craftbukkit.$MC_VERSION."

    fun getNMSClass(name: String): Class<*>? {
        return try {
            Class.forName(CODE_PATH_NMS + name)
        } catch (e: ClassNotFoundException) {
            Throwables.propagate(e)
            throw AssertionError()
        }
    }

    fun getNMSClassName(name: String): String {
        return CODE_PATH_NMS + name
    }

    fun getOBCClass(name: String): Class<*>? {
        return Class.forName(CODE_PATH_BUKKIT + name)
    }

    fun getBannerPatternJson(banner: ItemStack): String {
        val craftBanner = CraftItemStack.asNMSCopy(banner)
        val tag = craftBanner.tag ?: NBTTagCompound()
        return tag.toString()
    }
}