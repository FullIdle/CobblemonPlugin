package org.figsq.cobblemongrowth.cobblemongrowth

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.util.getPlayer
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.OfflinePlayer

object Papi: PlaceholderExpansion() {
    override fun getIdentifier(): String {
        return "cobblemongrowth"
    }

    override fun getAuthor(): String {
        return "FullIdle"
    }

    override fun getVersion(): String {
        return "1.0.0"
    }

    override fun onRequest(player: OfflinePlayer, params: String): String {
        return params.toIntOrNull()?.let {slot->
            Cobblemon.storage.getParty(player.uniqueId.getPlayer()!!).get(slot)?.let {poke->
                poke.getGrowthOrNull()?.name ?:"UNDEFINED"
            }?: "EMPTY-SLOT"
        }?: "UNKNOWN"
    }

    override fun persist(): Boolean {
        return true
    }
}