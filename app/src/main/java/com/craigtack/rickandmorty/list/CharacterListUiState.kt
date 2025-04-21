package com.craigtack.rickandmorty.list

import com.craigtack.rickandmorty.model.Character

sealed interface CharacterListUiState {
    object Loading : CharacterListUiState
    data class Success(val characters: List<Character>) : CharacterListUiState
    data class Error(val message : String) : CharacterListUiState
}
