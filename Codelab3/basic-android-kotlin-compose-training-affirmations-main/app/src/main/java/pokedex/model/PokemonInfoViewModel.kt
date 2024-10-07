package com.example.pokedex.pokedex.model

import com.example.pokedex.pokedex.api.ApiService
import com.example.pokedex.pokedex.api.Pokemon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface PokemonInfoCallback {
    fun onPokemonInfoReceived(pokemon: Pokemon)
    fun onError(t: Throwable)
}


class PokemonInfoViewModel {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: ApiService = retrofit.create(ApiService::class.java)

    fun getPokemonInfo(name: String, callback: PokemonInfoCallback) {
        val call = service.getPokemonInfo(name)

        call.enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                response.body()?.let { pokemon ->
                    callback.onPokemonInfoReceived(pokemon) // Llama al callback con el Pokémon
                } ?: callback.onError(Exception("Pokémon no encontrado")) // Manejo del caso donde no se encuentra el Pokémon
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                callback.onError(t) // Llama al callback en caso de error
            }
        })
    }
}
