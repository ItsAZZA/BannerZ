package com.itsazza.bannerz.util

import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.entity.Player

object Sounds {
    fun play(player: Player, sound: Sound?) {
        player.playSound(player.location, sound!!, 1f, 1f)
    }

    fun play(player: Player, sound: Sound?, pitch: Float) {
        player.playSound(player.location, sound!!, 1f, pitch)
    }

    fun play(location: Location, sound: Sound?) {
        location.world.playSound(location, sound!!, 1f, 1f)
    }
}