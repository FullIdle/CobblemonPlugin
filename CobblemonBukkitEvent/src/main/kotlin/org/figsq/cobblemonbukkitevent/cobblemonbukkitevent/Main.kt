package org.figsq.cobblemonbukkitevent.cobblemonbukkitevent

import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.cobblemon.mod.common.api.reactive.Observable
import com.cobblemon.mod.common.api.reactive.ObservableSubscription
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import kotlin.reflect.full.*
import kotlin.reflect.jvm.javaField

class Main : JavaPlugin() ,Listener{
    val subscribers = mutableListOf<ObservableSubscription<*>>()

    override fun onEnable() {
        for (member in CobblemonEvents::class.declaredMemberProperties)
            if (member.returnType.isSubtypeOf(Observable::class.starProjectedType)) {
                val observable = member.javaField!!.get(CobblemonEvents) as Observable<*>
                observable.subscribe {
                    Bukkit.getServer().pluginManager.callEvent(CobblemonEvent(it))
                }
            }
    }

    override fun onDisable() {
        subscribers.forEach { it.unsubscribe() }
    }
}