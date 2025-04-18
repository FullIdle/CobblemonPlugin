package org.figsq.cobblemonplugin.cobblemonplugin

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin(){
    override fun onEnable() {
        logger.info("§c=======================")
        val species = PokemonSpecies.random();
        logger.info(species.translatedName.string)
        logger.info("英语重找结果>>${PokemonSpecies.species.find { it.name == species.name }}")
        logger.info("中文重找结果>>${PokemonSpecies.species.find { it.translatedName.string == species.translatedName.string }}")
        logger.info("§c=======================")
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {

        return false
    }
}