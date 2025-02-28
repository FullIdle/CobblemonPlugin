package org.figsq.cblmworldnosent.cblmworldnosent

import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.cobblemon.mod.common.api.events.pokemon.PokemonSentPreEvent
import com.cobblemon.mod.common.api.reactive.ObservableSubscription
import net.minecraft.server.level.WorldServer
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    private lateinit var subscribeObj: ObservableSubscription<PokemonSentPreEvent>

    override fun onEnable() {
        this.reloadConfig()

        getCommand("cblmworldnosent")!!.setExecutor(this)

        subscribeObj = CobblemonEvents.POKEMON_SENT_PRE.subscribe { e ->
            e.pokemon.getOwnerUUID()?.let {ownerUUID->
                if (this.config.getStringList("worlds").contains((e.level as WorldServer).world.name)) {
                    Bukkit.getPlayer(ownerUUID)?.let {
                        e.cancel()
                        if (this.config.getBoolean("msg.enable")) {
                            it.sendMessage(this.config.getString("msg.text")!!.replace('&', '§'))
                        }
                    }
                }
            }
        }
        logger.info("§a>>enabled!<<")
    }

    override fun reloadConfig() {
        this.saveDefaultConfig()
        super.reloadConfig()
    }

    override fun onDisable() {
        this.subscribeObj.unsubscribe()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (!sender.isOp) {
            sender.sendMessage("§cYou don't have permission to use this command.")
        }
        this.reloadConfig()
        sender.sendMessage("§aReloaded config.")
        return false
    }
}