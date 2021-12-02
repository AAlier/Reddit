package com.reddit.rickandmortyapp.main.api.model

import com.reddit.rickandmortyapp.main.api.RickAndMortyCharacterResponse
import com.reddit.rickandmortyapp.main.model.RickMortyCharacter

object CharacterConverter {

    fun fromNetwork(item: RickAndMortyCharacterResponse): RickMortyCharacter {
        return RickMortyCharacter(
            id = item.id,
            name = item.name,
            image = item.image,
            url = item.url,
            createdAt = item.createdAt
        )
    }
}