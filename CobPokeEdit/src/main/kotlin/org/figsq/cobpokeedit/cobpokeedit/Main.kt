package org.figsq.cobpokeedit.cobpokeedit

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    override fun onEnable() {
        val command = getCommand("cobpokeedit")
        command?.let {
            it.setExecutor(this)
            it.setTabCompleter(this)
        } ?: logger.warning("Failed to register command")
    }


    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        return super.onCommand(sender, command, label, args)
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String>? {
        return super.onTabComplete(sender, command, alias, args)
    }
}