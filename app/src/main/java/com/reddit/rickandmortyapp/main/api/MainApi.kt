package com.reddit.rickandmortyapp.main.api

import retrofit2.http.GET
import retrofit2.http.Query

interface MainApi {
    @GET("api/character")
    suspend fun fetchCharactersCoroutine(@Query("page") page: Int = 1): RickAndMortyCharactersResponse
}