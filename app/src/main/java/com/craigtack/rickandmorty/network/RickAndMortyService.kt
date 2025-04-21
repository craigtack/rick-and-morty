package com.craigtack.rickandmorty.network

import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyService {
    @GET("character")
    suspend fun getCharacters(@Query("name") name: String): ApiResponse
}
