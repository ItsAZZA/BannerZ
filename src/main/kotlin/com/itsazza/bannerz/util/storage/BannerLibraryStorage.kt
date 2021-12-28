package com.itsazza.bannerz.util.storage

import com.itsazza.bannerz.BannerZPlugin
import com.itsazza.bannerz.builder.banner
import com.itsazza.bannerz.util.item
import com.itsazza.bannerz.util.mutateMeta
import org.bukkit.ChatColor
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.block.banner.Pattern
import org.bukkit.block.banner.PatternType
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BannerMeta
import org.bukkit.inventory.meta.ItemMeta
import java.io.File
import java.util.*

class BannerCategory(val name: String, var icon: ItemStack, var description: List<String>, var banners: MutableMap<String, ItemStack>)

object BannerLibraryStorage {
    val categories = HashMap<String, BannerCategory>()
    private val bannerConfigurationFile = File(BannerZPlugin.instance.dataFolder, "/categories")
    private val configurations = HashMap<String, YamlConfiguration>()

    private fun loadConfigurationFiles() {
        categories.clear()

        if (!bannerConfigurationFile.exists()) {
            bannerConfigurationFile.mkdir()
            return
        }

        val files = bannerConfigurationFile.listFiles() ?: return

        files.forEach {
            val id = it.name.dropLast(4).lowercase()
            val config = YamlConfiguration.loadConfiguration(it).also { config -> configurations[id] = config }
            val description = config.getStringList("category.description")

            val bannerKeys = config.getConfigurationSection("banners")?.getKeys(false) ?: return@forEach
            val banners = hashMapOf<String, ItemStack>()

            for (key in bannerKeys) {
                val bannerNameConfig = config.getString("banners.$key.name") ?: "Unknown"
                val bannerName = ChatColor.translateAlternateColorCodes('&', bannerNameConfig)
                val base = config.getString("banners.$key.base") ?: continue
                val bannerBase = Material.getMaterial(base) ?: continue
                val patterns = config.getStringList("banners.$key.patterns")

                banners[key] = banner(bannerBase) {
                    patterns.forEach { pattern ->
                        val data = pattern.split(" ")
                        pattern(Pattern(DyeColor.valueOf(data.first()), PatternType.valueOf(data.last())))
                    }
                }.mutateMeta<ItemMeta> { item ->
                    item.setDisplayName(ChatColor.translateAlternateColorCodes('&', bannerName))
                }
            }

            categories[id] = BannerCategory(
                config.getString("category.name") ?: "Unknown",
                Material.getMaterial(config.getString("category.icon") ?: "GRASS_BLOCK")?.item ?: Material.GRASS_BLOCK.item,
                description.map { desc -> "§7$desc" },
                banners.toList().sortedBy { banner -> banner.second.itemMeta!!.displayName}.toMap().toMutableMap()
            )
        }
    }

    fun searchForBanners(query: String): List<ItemStack> {
        val bannerList = mutableListOf<ItemStack>()
        categories.values.forEach {
            bannerList += it.banners.values.filter { item ->
                item.hasItemMeta()
                        && item.itemMeta!!.hasDisplayName()
                        && item.itemMeta!!.displayName.contains(query, true)
            }

        }
        return bannerList
    }

    fun addBanner(item: ItemStack, category: String) : Boolean {
        val name = category.lowercase()
        if (!categories.containsKey(name)) return false
        val yaml = configurations[category]!!
        val key = UUID.randomUUID().toString()

        val data = categories[name]!!
        val banners = data.banners
        banners[key] = item

        val sorted = banners.toList().sortedBy { banner -> banner.second.itemMeta!!.displayName}.toMap().toMutableMap()
        data.banners = sorted

        val meta = item.itemMeta as BannerMeta
        yaml.set("banners.$key.name", if (meta.hasDisplayName()) meta.displayName.replace("§", "&") else "&6Banner")
        yaml.set("banners.$key.base", item.type.name)
        yaml.set("banners.$key.patterns", meta.patterns.map { pattern -> "${pattern.color.name} ${pattern.pattern}" })
        configurations[category] = yaml

        saveCategory(name)
        return true
    }

    fun removeBanner(category: String, index: String) {
        val name = category.lowercase()
        if (!categories.containsKey(name)) return

        val data = categories[name]!!
        val banners = data.banners
        banners.remove(index)
        data.banners = banners

        val yaml = configurations[category] ?: return
        yaml.set("banners.$index", null)
        configurations[category] = yaml

        saveCategory(name)
    }

    fun addCategory(name: String): Boolean {
        val categoryID = name.replace(" ", "").lowercase()
        if (categoryExists(categoryID)) return false

        categories[categoryID] = BannerCategory(
            name,
            Material.GRASS_BLOCK.item,
            arrayListOf("Default description", "for a banner category"),
            hashMapOf()
        )

        configurations[categoryID] = YamlConfiguration().also { config ->
            config.set("category.name", name)
            config.set("category.description", listOf("Default description", "for a banner category"))
            config.set("category.icon", "GRASS_BLOCK")
        }

        saveCategory(categoryID)
        return true
    }

    fun removeCategory(name: String) : Boolean {
        val category = name.lowercase()
        if (!categoryExists(category)) return false

        categories.remove(category)
        File(bannerConfigurationFile, "${category}.yml").delete()
        return true
    }

    fun getCategory(name: String) : BannerCategory? {
        val category = name.lowercase()
        if (!categories.containsKey(category)) return null
        return categories[category]
    }

    fun categoryExists(name: String) : Boolean {
        return categories.containsKey(name.lowercase())
    }

    fun categoryHasBannerIndex(name: String, index: String) : Boolean {
        val category = getCategory(name.lowercase()) ?: return false
        return category.banners.containsKey(index)
    }

    fun setCategoryIcon(name: String, item: ItemStack) : Boolean {
        val category = name.lowercase()
        val data = categories[name] ?: return false
        data.icon = item

        val yaml = configurations[name] ?: return false
        yaml.set("category.icon", item.type.name)
        configurations[name] = yaml

        saveCategory(category)
        return true
    }

    fun setCategoryDescription(name: String, description: ArrayList<String>) : Boolean {
        val category = name.lowercase()
        val data = categories[name] ?: return false
        data.description = description

        val yaml = configurations[name] ?: return false
        yaml.set("category.description", description)
        configurations[name] = yaml

        saveCategory(category)
        return true
    }

    private fun saveCategory(name: String) {
        configurations[name]?.save(File(bannerConfigurationFile, "${name}.yml")) ?: throw IllegalStateException("Configuration for category $name is null")
    }

    fun saveCategories() {
        categories.forEach { saveCategory(it.key) }
    }

    fun load() {
        this.loadConfigurationFiles()
    }
}