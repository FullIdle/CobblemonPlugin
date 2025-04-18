package org.figsq.cobpokeedit.cobpokeedit.gui

import com.cobblemon.mod.common.item.PokemonItem
import com.cobblemon.mod.common.util.getPlayer
import com.cobblemon.mod.common.util.party
import me.fullidle.ficore.ficore.common.api.ineventory.ListenerInvHolder
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_21_R1.inventory.CraftItemStack
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.UUID

class SelectPokeGui(
    val target:UUID
) : ListenerInvHolder() {
    private val inv = Bukkit.createInventory(null, 54, "Select Pokemon").apply {
        val player = target.getPlayer() ?: throw Exception("Player not found")
        val party = player.party()
        for (pokemon in party) {
            val itemStack = PokemonItem.from(pokemon).asBukkitItemStack()
            val itemMeta = itemStack.itemMeta!!
            itemMeta.setDisplayName(pokemon.getDisplayName().string)
            itemStack.setItemMeta(itemMeta)
            addItem(itemStack)
        }
    }

    init {
        onClick {
            it.isCancelled = true
            val slot = it.slot
            val serverPlayer = target.getPlayer() ?: run {
                it.whoClicked.sendMessage("Player not found")
                it.whoClicked.closeInventory()
                return@onClick
            }
            val party = serverPlayer.party()
            party
        }
    }

    override fun getInventory(): Inventory {
        return inv
    }


    companion object {

    }
}


fun net.minecraft.world.item.ItemStack.asBukkitItemStack(): ItemStack {
    return CraftItemStack.asBukkitCopy(this)
}