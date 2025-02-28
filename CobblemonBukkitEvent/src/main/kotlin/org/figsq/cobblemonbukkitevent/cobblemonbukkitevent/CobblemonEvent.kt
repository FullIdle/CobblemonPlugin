package org.figsq.cobblemonbukkitevent.cobblemonbukkitevent

import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class CobblemonEvent(val cobblemonEvent: Any?) : Event(!Bukkit.getServer().isPrimaryThread) {
    override fun getHandlers(): HandlerList {
        return Companion.handlers
    }

    companion object {
        @JvmStatic
        private val handlers: HandlerList = HandlerList()
        @JvmStatic
        fun getHandlerList(): HandlerList {
            return handlers
        }
    }
}
