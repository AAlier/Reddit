package com.reddit.rickandmortyapp.main.api

import com.reddit.rickandmortyapp.main.api.model.CharacterConverter
import com.reddit.rickandmortyapp.main.model.RickMortyCharacter

class MainRepositoryImpl(
    private val mainApi: MainApi
) : MainRepository {

    override suspend fun loadCharacters(page: Int): List<RickMortyCharacter> {
        return mainApi.fetchCharactersCoroutine(page).results
            .map(CharacterConverter::fromNetwork)
    }
}