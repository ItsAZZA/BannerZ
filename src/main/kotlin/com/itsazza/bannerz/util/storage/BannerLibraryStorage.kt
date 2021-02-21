package com.itsazza.bannerz.util.storage

import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import com.itsazza.bannerz.BannerZPlugin
import com.itsazza.bannerz.builder.banner
import com.itsazza.bannerz.util.item
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BannerMeta
import org.bukkit.util.io.BukkitObjectInputStream
import org.bukkit.util.io.BukkitObjectOutputStream
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException
import java.io.*
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

data class BannerCategoryData(val name: String, val icon: String, val description: ArrayList<String>, val banners: ArrayList<BannerData>)
data class BannerData(val name: String, val baseBanner: String, val bannerMetaBytes: String)

class BannerCategory(val name: String, val icon: ItemStack, val description: ArrayList<String>, val banners: ArrayList<Banner>)
class Banner(val name: String, val item: ItemStack)

object BannerLibraryStorage {
    val categories = HashMap<String, BannerCategory>()

    fun loadConfigurationFiles() {
        categories.clear()

        val bannerConfigurationFile = File(BannerZPlugin.instance!!.dataFolder, "/categories")

        if (!bannerConfigurationFile.exists()) {
            bannerConfigurationFile.mkdir()
            return
        }

        val files = bannerConfigurationFile.listFiles() ?: return
        val gson = Gson()

        files.forEach {
            val bufferedReader = BufferedReader(FileReader(it))
            val bannerCategoryData = gson.fromJson(bufferedReader, BannerCategoryData::class.java)

            val name = bannerCategoryData.name
            val icon = Material.getMaterial(bannerCategoryData.icon) ?: Material.GRASS_BLOCK

            val bannerItems = ArrayList<Banner>()
            bannerCategoryData.banners.forEach { data ->
                val bannerMaterial = Material.getMaterial(data.baseBanner) ?: Material.WHITE_BANNER
                val bannerMeta = deSerializeBannerMeta(data.bannerMetaBytes)
                val patterns = bannerMeta.patterns
                bannerItems.add(Banner(it.name, banner(bannerMaterial) { patterns.forEach { pattern -> pattern(pattern) } }))
            }

            val bannerCategory = BannerCategory(
                name,
                icon.item,
                bannerCategoryData.description,
                bannerItems
            )

            categories[name.toLowerCase()] = bannerCategory
        }
    }

    fun broadcastBytes(bannerMeta: BannerMeta) {
        val serialized = serializeBannerMeta(bannerMeta)
        Bukkit.getServer().broadcastMessage(serialized)
    }

    private fun serializeBannerMeta(bannerMeta: BannerMeta) : String {
        val outputStream = ByteArrayOutputStream()
        val dataOutPut = BukkitObjectOutputStream(outputStream)

        dataOutPut.writeObject(bannerMeta)
        dataOutPut.close()

        return Base64.getEncoder().encodeToString(outputStream.toByteArray())
    }

    private fun deSerializeBannerMeta(serialized: String) : BannerMeta {
        val inputStream = ByteArrayInputStream(Base64.getDecoder().decode(serialized))
        val dataInput = BukkitObjectInputStream(inputStream)

        val bannerMeta = dataInput.readObject() as? BannerMeta ?: throw IllegalArgumentException()
        dataInput.close()

        return bannerMeta
    }
}