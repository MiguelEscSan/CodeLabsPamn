package com.example.pokedex.pokedex.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PokemonApiResponse (
    // The count field is the total number of results in the response
    @Expose @SerializedName("count") val count: Int,
    // The next and previous fields are URLs to the next and previous pages of results
    @Expose @SerializedName("next") val next: String,
    // The previous field is a URL to the previous page of results
    @Expose @SerializedName("previous") val previous: String,
    // The results field is a list of PokeResult objects
    @Expose @SerializedName("results") val results: List<PokeResult>
)