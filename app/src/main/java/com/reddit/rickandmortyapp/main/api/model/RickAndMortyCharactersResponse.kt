package com.reddit.rickandmortyapp.main.api

import com.google.gson.annotations.SerializedName
import java.util.*

// link to API Documentation: https://rickandmortyapi.com/documentation#character

data class RickAndMortyCharactersResponse(
    @SerializedName("results")
    val results: List<RickAndMortyCharacterResponse>
)

data class RickAndMortyCharacterResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("created")
    val createdAt: Calendar,
)