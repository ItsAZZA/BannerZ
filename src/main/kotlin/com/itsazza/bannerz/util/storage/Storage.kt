package com.itsazza.bannerz.util.storage

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.sql.Connection
import java.sql.DriverManager

class Storage(dbFile: File) {
    private val connection: Connection = DriverManager.getConnection("jdbc:sqlite:${dbFile.absolutePath}")
    private val type = object: TypeToken<MutableList<String>>() {}.type
    var gson = Gson()

    init {
        connection.createStatement().use { statement ->
            statement.execute(
                """
                CREATE TABLE IF NOT EXISTS data
                (
                    player_uuid TEXT PRIMARY KEY NOT NULL,
                    contents TEXT NOT NULL
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

    fun savePlayer(player: String, data: MutableList<String>) {
        connection.prepareStatement("REPLACE INTO data (player_uuid, contents) values(?,?)").use {
            it.setString(1, player)
            it.setString(2, gson.toJson(data))
            it.execute()
        }
    }
}