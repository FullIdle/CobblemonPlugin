package com.figsq.cobblemonplugin.taboolib.test

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.google.common.collect.Lists
import net.minecraft.server.level.ServerPlayer
import org.bukkit.Bukkit
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.info
import java.util.*


object Main : Plugin() {
    override fun onEnable() {
        info("Hello World!")

        CobblemonEvents.BATTLE_FLED.subscribe {
            val entity = it.player.entity!!
            val player = Bukkit.getPlayer(entity.uuid)!!
            player.sendMessage("你逃跑了")
            val size =
                Lists.newArrayList(Cobblemon.storage.getParty(entity)).apply {
                    this.removeIf(Objects::isNull)
                    for (pokemon in this) {
                        pokemon.entity?.let {
                            info(it.uuid)
                        }
                    }
                }.size
            player.sendMessage("并且你的 $size 只宝可梦和你一起逃跑了")
        }
    }
}