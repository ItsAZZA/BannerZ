package com.itsazza.bannerz.util.storage

import com.itsazza.bannerz.BannerZPlugin
import com.itsazza.bannerz.builder.banner
import com.itsazza.bannerz.util.toBoolean
import com.itsazza.bannerz.util.toInt
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.block.banner.Pattern
import org.bukkit.block.banner.PatternType
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BannerMeta
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.*

object Storage {
    private val config = BannerZPlugin.instance.config
    private val connection: Connection = getConnection()

    init {
        connection.createStatement().also { statement ->
            statement.execute(
                """
                CREATE TABLE IF NOT EXISTS data
                (
                    id INTEGER NOT NULL PRIMARY KEY,
                    player_uuid CHAR(36) NOT NULL,
                    banner_name TEXT NOT NULL,
                    banner_base VARCHAR(32) NOT NULL,
                    banner_data TEXT NOT NULL
                );
            """.trimIndent()
            )
        }

        connection.createStatement().also { statement ->
            statement.execute(
                """
                    CREATE TABLE IF NOT EXISTS player_settings
                    (
                        player_uuid CHAR(36) PRIMARY KEY,
                        visitors INTEGER DEFAULT 1
                    );
                """.trimIndent()
            )
        }
    }

    fun loadPlayer(playerUUID: String): HashMap<Int, ItemStack>? {
        connection.createStatement().also { statement ->
            statement.executeQuery("SELECT * FROM data WHERE player_uuid = '$playerUUID'").also { resultSet ->
                val banners = hashMapOf<Int, ItemStack>()
                while (resultSet.next()) {
                    val name = resultSet.getString("banner_name")
                    val id = resultSet.getInt("id")
                    val base = resultSet.getString("banner_base")
                    val data = resultSet.getString("banner_data")
                    banners.also {
                        it[id] = banner(Material.valueOf(base), name) {
                            data.split(",").forEach {
                                val bannerData = it.split(" ")
                                val color = DyeColor.valueOf(bannerData.first())
                                val pattern = PatternType.valueOf(bannerData.last())
                                pattern(Pattern(color, pattern))
                            }
                        }
                    }
                }
                return banners
            }
        }
    }

    fun addBanner(playerUUID: String, banner: ItemStack) {
        connection.prepareStatement("REPLACE INTO data (player_uuid, banner_name, banner_base, banner_data) VALUES (?, ?, ?, ?)")
            .also { statement ->
                statement.setString(1, playerUUID)
                statement.setString(2, banner.itemMeta!!.displayName)
                statement.setString(3, banner.type.name)

                val bannerMeta = banner.itemMeta as BannerMeta
                statement.setString(4, bannerMeta.patterns.joinToString(",") { "${it.color.name} ${it.pattern.name}" })
                statement.execute()
            }
    }

    fun updateBanner(bannerID: Int, banner: ItemStack) {
        connection.prepareStatement("UPDATE data SET banner_base = ?, banner_data = ? WHERE id = ?")
            .also { statement ->
                statement.setString(1, banner.type.name)

                val bannerMeta = banner.itemMeta as BannerMeta
                statement.setString(2, bannerMeta.patterns.joinToString(",") { "${it.color.name} ${it.pattern.name}" })
                statement.setInt(3, bannerID)
                statement.execute()
            }
    }

    fun setBannerName(bannerID: Int, name: String) {
        connection.prepareStatement("UPDATE data SET banner_name = ? WHERE id = ?")
            .also { statement ->
                statement.setString(1, name)
                statement.setInt(2, bannerID)
                statement.execute()
            }
    }

    fun removeBanner(playerUUID: String, bannerID: Int) {
        connection.prepareStatement("DELETE FROM data WHERE player_uuid = ? AND id = ?").also { statement ->
            statement.setString(1, playerUUID)
            statement.setInt(2, bannerID)
            statement.execute()
        }
    }

    fun getVisitorStatus(playerUUID: String): Boolean {
        connection.createStatement().also { statement ->
            statement.executeQuery("SELECT visitors FROM player_settings WHERE player_uuid = '$playerUUID'").also { resultSet ->
                if (resultSet.next()) {
                    return resultSet.getInt(1).toBoolean()
                }
            }
        }
        return false
    }

    fun setVisitorStatus(player: String, visitorStatus: Boolean) {
        connection.prepareStatement("INSERT OR REPLACE INTO player_settings (player_uuid, visitors) VALUES (?,?)").also {
            it.setString(1, player)
            it.setInt(2, visitorStatus.toInt())
            it.execute()
        }
    }


    private fun getConnection(): Connection {
        val mySQLEnabled = config.getBoolean("settings.database.mysql.enabled")
        return if (mySQLEnabled) getMySQLConnection()!! else getSQLiteConnection()
    }

    private fun getSQLiteConnection(): Connection {
        val db = config.getString("settings.database.sqlite.db")
        return DriverManager.getConnection(
            "jdbc:sqlite:${
                File(
                    BannerZPlugin.instance.dataFolder,
                    "$db.db"
                ).absolutePath
            }"
        )
    }

    private fun getMySQLConnection(): Connection? {
        val host = config.getString("settings.database.mysql.host")
        val port = config.getString("settings.database.mysql.port")
        val user = config.getString("settings.database.mysql.user")
        val password = config.getString("settings.database.mysql.password")
        val database = config.getString("settings.database.mysql.database")

        val properties = Properties()
        properties.setProperty("user", user)
        properties.setProperty("password", password)

        try {
            return DriverManager.getConnection("jdbc:mysql://$host:$port/$database", properties)
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return null
    }
}