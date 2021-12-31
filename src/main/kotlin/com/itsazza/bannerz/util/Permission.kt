package com.itsazza.bannerz.util

import org.bukkit.entity.Player

fun checkPermission(player: Player, permission: String) : Boolean {
    if (!player.hasPermission(permission)) {
        player.sendMessage("§cInsufficient permission: $permission")
        return false
    }
    return true
}