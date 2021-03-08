package com.itsazza.bannerz.util.storage

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.itsazza.bannerz.BannerZPlugin
import com.itsazza.bannerz.util.toBoolean
import com.itsazza.bannerz.util.toInt
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.*

object Storage {
    private val config = BannerZPlugin.instance.config
    private val connection: Connection = getConnection()
    private val type = object: TypeToken<MutableList<String>>() {}.type
    private var gson = Gson()

    init {
        connection.createStatement().use { statement ->
            statement.execute(
                """
                CREATE TABLE IF NOT EXISTS data
                (
                    player_uuid TEXT PRIMARY KEY NOT NULL,
                    contents TEXT NOT NULL,
                    visitors INTEGER DEFAULT 1 NOT NULL
                );
            """.trimIndent()
            )
        }
    }

    fun loadPlayer(player: String) : MutableList<String>? {
        connection.createStatement().also { statement ->
            statement.executeQuery("SELECT contents as json FROM data WHERE player_uuid = '$player'").use { resultSet ->
                if(resultSet.next()) {
                    return gson.fromJson(resultSet.getString(1), type)
                }
            }
        }
        return null
    }

    fun getVisitorStatus(playerUUID: String) : Boolean {
        connection.createStatement().also { statement ->
            statement.executeQuery("SELECT visitors FROM data WHERE player_uuid = '$playerUUID'").use { resultSet ->
                if(resultSet.next()) {
                    return resultSet.getInt(1).toBoolean()
                }
            }
        }
        return false
    }

    fun setVisitorStatus(player: String, visitorStatus: Boolean) {
        connection.prepareStatement("UPDATE data SET visitors = (?) WHERE player_uuid = '$player'").use {
            it.setInt(1, visitorStatus.toInt())
            it.execute()
        }
    }

    fun savePlayer(player: String, data: MutableList<String>) {
        connection.prepareStatement("REPLACE INTO data (player_uuid, contents) values(?,?)").use {
            it.setString(1, player)
            it.setString(2, gson.toJson(data))
            it.execute()
        }
    }

    fun deletePlayersWithNoBanners() {
        connection.prepareStatement("DELETE FROM data WHERE contents = '[]'").execute()
    }

    private fun getConnection() : Connection {
        val mySQLEnabled = config.getBoolean("settings.database.mysql.enabled")
        val sqlite = getSQLiteConnection()
        if (mySQLEnabled) {
            return getMySQLConnection() ?: return sqlite
        }
        return sqlite
    }

    private fun getSQLiteConnection() : Connection {
        val db = config.getString("settings.database.sqlite.db")
        return DriverManager.getConnection("jdbc:sqlite:${File(BannerZPlugin.instance.dataFolder, "$db.db").absolutePath}")
    }

    private fun getMySQLConnection() : Connection? {
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