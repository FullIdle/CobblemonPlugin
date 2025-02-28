package org.figsq.cobblemongrowth.cobblemongrowth

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import com.cobblemon.mod.common.pokemon.Pokemon

class Growth(var name:String,var scaleValue: Float,var rarity:Int) {
    fun modifyPoke(pokemonEntity: PokemonEntity) {
        pokemonEntity.pokemon.scaleModifier = scaleValue
    }
}

fun Pokemon.getGrowthOrNull(): Growth? {
    return Main.loadGrowth.find { it.scaleValue == this.scaleModifier }
}