package com.itsazza.bannerz.util

import org.bukkit.entity.Player

object Permissions {
    fun check(player: Player, permission: String, silent: Boolean = false) : Boolean {
        if (!player.hasPermission(permission)) {
            if (!silent) player.sendMessage("§cInsufficient permission: $permission")
            return false
        }
        return true
    }

    fun getNumberFromPermission(player: Player, permission: String?, defaultValue: Int): Int {
        val permissions = player.effectivePermissions
        var set = false
        var highest = 0
        for (info in permissions) {
            val perm = info.permission
            if (!perm.startsWith(permission!!)) {
                continue
            }
            val index = perm.lastIndexOf('.')
            if (index == -1 || index == perm.length) {
                continue
            }
            val numStr = perm.substring(perm.lastIndexOf('.') + 1)
            if (numStr == "*") {
                return defaultValue
            }
            val number = numStr.toInt()
            if (number >= highest) {
                highest = number
                set = true
            }
        }
        return if (set) highest else defaultValue
    }
}