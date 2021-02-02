package com.itsazza.bannerz.util

import org.bukkit.Material
import org.bukkit.inventory.Inventory

fun Inventory.hasItems(items: Map<Material, Int>): Boolean {
    val contents = this.storageContents

    for (item in items) {
        var amountInInventory = 0

        for (slotItem in contents) {
            slotItem ?: continue
            if (slotItem.type == item.key) {
                amountInInventory += slotItem.amount
            }

            if (amountInInventory >= item.value) break
        }

        if (amountInInventory < item.value) return false
    }
    return true
}

fun Inventory.takeItems(items: Map<Material, Int>) {
    items.forEach {
        var amount = it.value
        if (amount <= 0) return
        val contents = this.storageContents

        for (slotItem in contents) {
            slotItem ?: continue
            if (slotItem.type == it.key) {
                val newAmount = slotItem.amount - amount
                if (newAmount > 0) {
                    slotItem.amount = newAmount
                    break
                } else {
                    slotItem.amount = 0
                    amount = -newAmount
                    if (amount == 0) break
                }
            }
            this.storageContents = contents
        }
    }
}