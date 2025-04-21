package com.craigtack.rickandmorty.network

import com.craigtack.rickandmorty.di.IoDispatcher
import com.craigtack.rickandmorty.model.Character
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface CharacterRepository {
    suspend fun getCharacters(name: String): List<Character>
}

class CharacterRepositoryImpl @Inject constructor(
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
    private val rickAndMortyService: RickAndMortyService,
) : CharacterRepository {

    override suspend fun getCharacters(name: String): List<Character> {
        return withContext(ioDispatcher) {
            rickAndMortyService.getCharacters(name).results
                .map {
                    Character(
                        name = it.name,
                        image = it.image,
                        species = it.species,
                        status = it.status,
                        originName = it.origin.name,
                        type = it.type,
                        created = it.created,
                    )
                }
        }
    }
}
