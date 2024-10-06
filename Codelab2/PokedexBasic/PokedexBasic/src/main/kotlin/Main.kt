package org.example

import com.example.pokedex.pokedex.model.PokemonListViewModel

fun main() {

    val viewModel = PokemonListViewModel()

    // Llamar a getPokemonList para obtener la lista de Pokémon
    viewModel.getPokemonList()
}