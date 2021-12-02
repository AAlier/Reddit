package com.reddit.rickandmortyapp.main.model

import java.util.*

data class RickMortyCharacter(
    val id: Int,
    val name: String,
    val image: String,
    val url: String,
    val createdAt: Calendar,
)