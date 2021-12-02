package com.reddit.rickandmortyapp.main.api

import com.reddit.rickandmortyapp.main.model.RickMortyCharacter

interface MainRepository {
    suspend fun loadCharacters(page: Int): List<RickMortyCharacter>
}