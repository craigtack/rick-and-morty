package com.craigtack.rickandmorty.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.craigtack.rickandmorty.R
import com.craigtack.rickandmorty.model.Character

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListScreen(
    modifier: Modifier = Modifier,
    viewModel: CharacterListViewModel = hiltViewModel(),
    onNavigateToDetails: (Character) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = modifier.fillMaxSize()) {
        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    query = viewModel.textFieldState.text.toString(),
                    onQueryChange = { viewModel.textFieldState.edit { replace(0, length, it) } },
                    onSearch = {},
                    expanded = false,
                    onExpandedChange = {},
                    placeholder = {
                        Text(stringResource(R.string.search_hint))
                    }
                )
            },
            expanded = viewModel.textFieldState.text.isNotEmpty(),
            onExpandedChange = {},
        ) {
            Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
                when (val newUiState = uiState) {
                    is CharacterListUiState.Loading -> {
                        CircularProgressIndicator()
                    }
                    is CharacterListUiState.Success -> {
                        CharacterList(newUiState.characters) { character ->
                            onNavigateToDetails(character)
                        }
                    }
                    is CharacterListUiState.Error -> {
                        Text(stringResource(R.string.an_error_occurred, newUiState.message))
                    }
                }
            }
        }
    }

    LaunchedEffect(true) {
        viewModel.run()
    }
}

@Composable
fun CharacterList(
    characters: List<Character>,
    modifier: Modifier = Modifier,
    onCharacterClick: (Character) -> Unit,
) {
    LazyVerticalGrid(modifier = modifier, columns = GridCells.Fixed(2)) {
        items(characters) { character ->
            CharacterItem(character) {
                onCharacterClick(it)
            }
        }
    }
}

@Composable
fun CharacterItem(
    character: Character,
    modifier: Modifier = Modifier,
    onClick: (Character) -> Unit,
) {
    Column(modifier = modifier.fillMaxWidth().clickable { onClick(character) }) {
        AsyncImage(
            model = character.image,
            contentDescription = stringResource(R.string.character_image_content_description),
        )
        Text(character.name)
    }
}
