package org.figsq.cblmendbattle.cblmendbattle

import com.cobblemon.mod.common.battles.BattleRegistry
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin(){
    override fun onEnable() {
        this.reloadConfig()
        getCommand("endbattle")!!.setExecutor(this)
    }

    override fun reloadConfig() {
        this.saveDefaultConfig()
        super.reloadConfig()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        //reload
        args.getOrNull(0)?.let {
            if (it == "reload") {
                this.reloadConfig()
                sender.sendMessage("§aReloaded config.")
                return false
            }
        }

        //eb
        if (sender !is Player) {
            sender.sendMessage("§cYou must be a player to use this command.")
            return false
        }
        BattleRegistry.getBattleByParticipatingPlayerId(sender.uniqueId)?.let {
            it.end()
            sender.sendMessage(this.config.getString("msg")!!.replace('&', '§'))
            return false
        } ?: sender.sendMessage("§cYou are not in a battle.")
        return false
    }
}