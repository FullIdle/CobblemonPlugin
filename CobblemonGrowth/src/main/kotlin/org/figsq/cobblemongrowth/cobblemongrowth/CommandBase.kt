package org.figsq.cobblemongrowth.cobblemongrowth

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.util.getPlayer
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

object CommandBase: TabExecutor{
    val subCmd = listOf(
        "help","reload","getgrowth","setgrowth"
    )
    val helps = arrayOf(
        "helpmsg"
    )

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (!sender.isOp) {
            sender.sendMessage("This command is not available without op!!!")
            return false
        }
        args.getOrNull(0)?.let {sub ->
            val lowercase = sub.lowercase()
            when (lowercase) {
                "reload" -> {
                    sender.sendMessage("reload complete")
                    Main.instance.reloadConfig()
                    return false
                }
                "getgrowth","setgrowth" -> {
                    if (sender !is Player) {
                        sender.sendMessage("Only players can use this command!")
                        return false
                    }
                    args.getOrNull(1)?.let {slotArg->
                        slotArg.toIntOrNull()?.let {slot->
                            Cobblemon.storage.getParty(sender.uniqueId.getPlayer()!!).get(slot)?.let { poke->
                                if (lowercase == "setgrowth") {
                                    args.getOrNull(2)?.let {name->
                                        Main.loadGrowth.find { it.name == name }?.let {
                                            poke.scaleModifier = it.scaleValue
                                            sender.sendMessage("The size of this Pokémon has been set to ${it.name}")
                                            return false
                                        } ?: sender.sendMessage("Illegal Type! $name")
                                        return false
                                    } ?: sender.sendMessage("Please enter the preset growth type!")
                                    return false
                                }
                                poke.getGrowthOrNull()?.let {
                                    sender.sendMessage("This Pokémon's size is ${it.name}")
                                } ?:
                                sender.sendMessage("The size of this Pokémon is not preset by the plug-in!")
                                return false
                            } ?:
                            sender.sendMessage("No pokemon in this slot!")
                            return false
                        } ?:
                        sender.sendMessage("Illegal Type! $slotArg")
                        return false
                    }
                    sender.sendMessage("Please enter the backpack poke index number")
                    return false
                }
                else -> return@let
            }
        }
        sender.sendMessage(*helps)
        return false;
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): List<String> {
        return args.getOrNull(2)?.let {arg->
            if (arg.isEmpty()) {
                Main.loadGrowth.map { it.name }.toMutableList()
            }else Main.loadGrowth.filter { it.name.startsWith(arg) }.map { it.name }.toMutableList()
        }?:
        args.getOrNull(1)?.let {_->
            args[0].run {
                if (this == "setgrowth" || this == "getgrowth") {
                    (0..5).map { it.toString() }.toMutableList()
                } else null
            }
        }?:

        args.getOrNull(0)?.let {arg->
            subCmd.filter { it.startsWith(arg) }.toMutableList()
        } ?: subCmd.toMutableList()
    }
}