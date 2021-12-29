package com.itsazza.bannerz.menus.publiclibrary

import com.itsazza.bannerz.BannerZPlugin
import com.itsazza.bannerz.util.storage.BannerLibraryStorage
import org.bukkit.conversations.ConversationContext
import org.bukkit.conversations.ConversationFactory
import org.bukkit.conversations.Prompt
import org.bukkit.conversations.StringPrompt
import org.bukkit.entity.Player

object SearchConversation {
    private val instance = BannerZPlugin.instance

    fun start(player: Player) {
        val factory = ConversationFactory(instance)
            .withModality(true)
            .withLocalEcho(false)
            .withEscapeSequence("cancel")
            .withFirstPrompt(SearchPrompt(player))
            .withTimeout(60)

        factory.buildConversation(player).begin()
    }

    private class SearchPrompt(val player: Player) : StringPrompt() {
        override fun getPromptText(context: ConversationContext): String {
            return "\n§6§lWhat do you want to search for?\n§7Write what you want to search for or \"cancel\" to cancel."
        }

        override fun acceptInput(context: ConversationContext, input: String?): Prompt? {
            input ?: return null
            PublicLibraryMenu.open(player, "Results for ${input.lowercase()}", BannerLibraryStorage.searchForBanners(input))
            return END_OF_CONVERSATION
        }
    }
}