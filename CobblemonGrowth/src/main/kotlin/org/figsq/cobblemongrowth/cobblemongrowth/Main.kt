package org.figsq.cobblemongrowth.cobblemongrowth

import com.cobblemon.mod.common.api.events.CobblemonEvents
import net.minecraft.server.level.EntityPlayer
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftPlayer
import org.bukkit.plugin.java.JavaPlugin
import java.util.concurrent.ConcurrentSkipListMap
import kotlin.random.Random

class Main : JavaPlugin() {
    companion object {
        val instance: Main by lazy {
            getPlugin(Main::class.java)
        }

        val map: ConcurrentSkipListMap<Int, Growth> = ConcurrentSkipListMap()
        val loadGrowth = mutableListOf<Growth>()

        val subscribe = CobblemonEvents.POKEMON_ENTITY_SPAWN.subscribe { e ->
            e.entity.pokemon.scaleModifier = map.ceilingEntry(Random.nextInt(map.lastKey())).value.scaleValue
        }
    }

    override fun onEnable() {
        reloadConfig()
        Papi.register()
        val command = getCommand("cobblemongrowth")!!
        command.setExecutor(CommandBase)
        command.setTabCompleter(CommandBase)
        "plugin enabled!!!".info()
    }

    override fun reloadConfig() {
        this.saveDefaultConfig()
        super.reloadConfig()

        loadGrowth.clear()
        map.clear()
        val section = config.getConfigurationSection("growths")!!
        var i = 0
        for (key in section.getKeys(false)) {
            Growth(
                key,
                section.getDouble("$key.scale").toFloat(),
                section.getInt("$key.rarity")
            ).apply {
                if (this.rarity != 0) {
                    i += this.rarity
                    map[i] = this
                }
                loadGrowth.add(this)
            }
        }
    }

    override fun onDisable() {
        subscribe.unsubscribe()
        Papi.unregister()
    }
}

fun String.info() {
    Main.instance.logger.info(this)
}

fun String.warning() {
    Main.instance.logger.warning(this)
}

fun org.bukkit.entity.Player.getMcPlayer(): EntityPlayer? {
    return (this as CraftPlayer).handle
}
