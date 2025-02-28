package org.figsq.cobblemonplugin.cobblemonplugin

import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.cobblemon.mod.common.battles.BattleRegistry
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin(){
    override fun onEnable() {

    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        BattleRegistry.getBattleByParticipatingPlayerId((sender as Player).uniqueId)?.let {
            if (args!!.isEmpty()) {
                it.stop()
                return false
            }
            it.end()
        }
        return false
    }
}