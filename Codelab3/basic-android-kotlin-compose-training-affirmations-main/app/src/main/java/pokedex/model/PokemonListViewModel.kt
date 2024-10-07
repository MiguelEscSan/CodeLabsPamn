package com.example.pokedex.pokedex.model

import com.example.pokedex.pokedex.api.ApiService
import com.example.pokedex.pokedex.api.PokemonApiResponse
import com.example.pokedex.pokedex.api.Pokemon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class PokemonListViewModel(private val infoService: PokemonInfoViewModel) {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: ApiService = retrofit.create(ApiService::class.java)

    fun getPokemonList(callback: (List<Pokemon>) -> Unit) {
        val call = service.getPokemonList(151, 0)

        call.enqueue(object : Callback<PokemonApiResponse> {
            override fun onResponse(call: Call<PokemonApiResponse>, response: Response<PokemonApiResponse>) {
                if (response.isSuccessful) {
                    response.body()?.results?.let { list ->
                        val pokemonList = mutableListOf<Pokemon>()
                        val pendingRequests = list.size // Para contar las solicitudes pendientes

                        list.forEach { pokeResult ->
                            infoService.getPokemonInfo(pokeResult.name, object : PokemonInfoCallback {
                                override fun onPokemonInfoReceived(pokemon: Pokemon) {
                                    pokemonList.add(pokemon)

                                    if (pokemonList.size == pendingRequests) {
                                        val orderedPokemonList = pokemonList.sortedBy { it.id }
                                        callback(orderedPokemonList)
                                    }
                                }

                                override fun onError(t: Throwable) {
                                    println("Error al obtener info de Pokémon: ${t.message}")
                                }
                            })
                        }
                    }
                } else {
                    println("Error: ${response.code()} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PokemonApiResponse>, t: Throwable) {
                println("Error: ${t.message}")
            }
        })
    }
}
