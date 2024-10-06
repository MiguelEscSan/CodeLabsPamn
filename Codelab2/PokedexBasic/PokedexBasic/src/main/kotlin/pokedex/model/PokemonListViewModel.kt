package com.example.pokedex.pokedex.model

import com.example.pokedex.pokedex.api.ApiService
import com.example.pokedex.pokedex.api.PokemonApiResponse
import com.example.pokedex.pokedex.api.PokeResult
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonListViewModel {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: ApiService = retrofit.create(ApiService::class.java)

    fun getPokemonList() {
        val call = service.getPokemonList(151, 0)

        // Llamada síncrona
        val response: Response<PokemonApiResponse> = call.execute()
        if (response.isSuccessful) {
            response.body()?.results?.let { list ->
                // Aquí puedes manejar los resultados como desees
                println("Pokemon List:")
                list.forEach { pokemon ->
                    println(pokemon.name)
                }
            }
        } else {
            println("Error: ${response.code()} ${response.message()}")
        }
    }
}
