package org.figsq.cobblemonbukkitevent.cobblemonbukkitevent

import com.cobblemon.mod.common.api.events.CobblemonEvents
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    override fun onEnable() {
        CobblemonEvents.POKEMON_ENTITY_SPAWN.subscribe {
        }

        
    }
}