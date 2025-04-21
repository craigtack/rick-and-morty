package com.craigtack.rickandmorty.list

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.craigtack.rickandmorty.network.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val repository: CharacterRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<CharacterListUiState>(CharacterListUiState.Loading)
    val uiState: StateFlow<CharacterListUiState> = _uiState.asStateFlow()
    val textFieldState = TextFieldState()

    @OptIn(FlowPreview::class)
    suspend fun run() {
        snapshotFlow { textFieldState.text }
            .filter { it.isNotBlank() }
            .collectLatest { queryText ->
                performSearch(queryText.toString())
            }
    }

    // TODO: make private and test via run function
    fun performSearch(query: String) {
        viewModelScope.launch {
            try {
                _uiState.update { CharacterListUiState.Loading }
                val characters = repository.getCharacters(query)
                _uiState.update { CharacterListUiState.Success(characters) }
            } catch (e: Exception) {
                _uiState.update { CharacterListUiState.Error(e.message.toString()) }
            }
        }
    }
}
