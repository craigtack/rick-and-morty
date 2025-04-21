package com.craigtack.rickandmorty.network

data class CharacterApiModel(
    val name: String,
    val image: String,
    val species: String,
    val status: String,
    val origin: Origin,
    val type: String,
    val created: String,
)

data class Origin(
    val name: String,
)
