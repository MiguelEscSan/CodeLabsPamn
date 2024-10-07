package com.example.affirmations.data

import com.example.affirmations.model.PokeCard
import com.example.pokedex.pokedex.model.PokemonInfoViewModel
import com.example.pokedex.pokedex.model.PokemonListViewModel

class Datasource {
    private val pokeInfoViewModel = PokemonInfoViewModel()
    private val pokeListViewModel = PokemonListViewModel(pokeInfoViewModel)

    fun loadPokeCards(callback: (List<PokeCard>) -> Unit) {
        pokeListViewModel.getPokemonList { pokemonList ->
            callback(pokemonList.map { pokemon ->
                PokeCard(
                    id = pokemon.id,
                    name = pokemon.name,
                    imageUrl = pokemon.sprites.frontDefault.toString()
                )
            })
        }
    }

    private fun callback(map: List<Unit>) {
            TODO("Not yet implemented")
    }
}
