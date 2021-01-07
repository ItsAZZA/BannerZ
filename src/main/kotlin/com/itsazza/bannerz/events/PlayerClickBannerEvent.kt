package com.itsazza.bannerz.events

import com.itsazza.bannerz.menus.creator.BannerCreatorMenu
import com.itsazza.bannerz.util.isBanner
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

object PlayerClickBannerEvent : Listener {
    @EventHandler
    fun playerClickBannerEvent(event: PlayerInteractEvent) {
        if (event.action != Action.RIGHT_CLICK_BLOCK) return
        if (!event.player.isSneaking) return
        if (event.hand != EquipmentSlot.HAND) return
        if (event.player.inventory.itemInMainHand.type != Material.AIR) return

        val clickedBlock = event.clickedBlock ?: return
        if (!isBanner(clickedBlock.type)) return
        if (!event.player.hasPermission("bannerz.interact")) return
    }
}