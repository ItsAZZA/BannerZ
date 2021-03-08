package com.itsazza.bannerz.util.storage

import com.google.gson.Gson
import com.itsazza.bannerz.BannerZPlugin
import com.itsazza.bannerz.menus.playerlibrary.data.deSerializeItemStack
import com.itsazza.bannerz.menus.playerlibrary.data.serializeItemStack
import com.itsazza.bannerz.util.item
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.io.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

data class BannerCategoryData(val name: String, val icon: String, val description: ArrayList<String>, val banners: ArrayList<String>)
class BannerCategory(val name: String, var icon: ItemStack, var description: ArrayList<String>, var banners: ArrayList<ItemStack>)

object BannerLibraryStorage {
    val categories = HashMap<String, BannerCategory>()
    private val bannerConfigurationFile = File(BannerZPlugin.instance.dataFolder, "/categories")
    private val gson = Gson()

    private fun loadConfigurationFiles() {
        categories.clear()

        if (!bannerConfigurationFile.exists()) {
            bannerConfigurationFile.mkdir()
            return
        }

        val files = bannerConfigurationFile.listFiles() ?: return

        files.forEach {
            val bufferedReader = BufferedReader(FileReader(it))
            val category = gson.fromJson(bufferedReader, BannerCategoryData::class.java)

            val name = category.name
            val id = it.name.dropLast(5).toLowerCase()
            val icon = Material.getMaterial(category.icon) ?: Material.GRASS_BLOCK

            val listOfBanners = ArrayList<ItemStack>()
            for (banner in category.banners) {
                listOfBanners.add(deSerializeItemStack(banner))
            }

            listOfBanners.sortBy { banner -> banner.itemMeta.displayName }

            categories[id] = BannerCategory(
                name,
                icon.item,
                category.description,
                listOfBanners
            )
        }
    }

    fun addBanner(item: ItemStack, category: String) : Boolean {
        val name = category.toLowerCase()
        if (!categories.containsKey(name)) return false

        val data = categories[name]!!
        val banners = data.banners
        banners.add(item)

        banners.sortBy { banner -> banner.itemMeta.displayName }

        data.banners = banners
        saveCategory(name)
        return true
    }

    fun removeBanner(category: String, index: Int) {
        val name = category.toLowerCase()
        if (!categories.containsKey(name)) return

        val data = categories[name]!!
        val banners = data.banners
        banners.removeAt(index)
        data.banners = banners
        saveCategory(name)
    }

    fun addCategory(name: String): Boolean {
        val categoryID = name.replace(" ", "").toLowerCase()
        if (categoryExists(categoryID)) return false

        categories[categoryID] = BannerCategory(
            name,
            Material.GRASS_BLOCK.item,
            arrayListOf("Default description", "for banner category"),
            arrayListOf()
        )

        saveCategory(categoryID)
        return true
    }

    fun removeCategory(name: String) : Boolean {
        val category = name.toLowerCase()
        if (!categoryExists(category)) return false

        categories.remove(category)
        val file = File(bannerConfigurationFile, "${category}.json")
        file.delete()
        return true
    }

    fun getCategory(name: String) : BannerCategory? {
        val category = name.toLowerCase()
        if (!categories.containsKey(category)) return null
        return categories[category]
    }

    fun categoryExists(name: String) : Boolean {
        return categories.containsKey(name.toLowerCase())
    }

    fun categoryHasBannerIndex(name: String, index: Int) : Boolean {
        val category = getCategory(name.toLowerCase()) ?: return false
        return category.banners.size >= index + 1
    }

    fun setCategoryIcon(name: String, item: ItemStack) : Boolean {
        val category = name.toLowerCase()
        val data = categories[name] ?: return false
        data.icon = item

        saveCategory(category)
        return true
    }

    fun setCategoryDescription(name: String, description: ArrayList<String>) : Boolean {
        val category = name.toLowerCase()
        val data = categories[name] ?: return false
        data.description = description

        saveCategory(category)
        return true
    }

    private fun saveCategory(name: String) {
        val category = name.toLowerCase()
        if (!categories.containsKey(category)) return
        val file = File(bannerConfigurationFile, "${category}.json")

        if (!bannerConfigurationFile.exists()) {
            file.mkdirs()
        }

        if (!file.exists()) {
            file.createNewFile()
        }

        val data = categories[category]!!

        val listOfBanners = ArrayList<String>()
        data.banners.forEach {
            listOfBanners.add(serializeItemStack(it))
        }

        val bannerCategory = BannerCategoryData(
            data.name,
            data.icon.type.name,
            data.description,
            listOfBanners
        )

        val writer = BufferedWriter(FileWriter(file))
        writer.write(gson.toJson(bannerCategory, BannerCategoryData::class.java))
        writer.close()
    }

    fun saveCategories() {
        for (category in categories) {
            saveCategory(category.key)
        }
    }

    fun load() {
        this.loadConfigurationFiles()
    }
}