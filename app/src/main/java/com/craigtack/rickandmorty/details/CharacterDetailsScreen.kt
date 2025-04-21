package com.craigtack.rickandmorty.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.craigtack.rickandmorty.CharacterDetails
import com.craigtack.rickandmorty.R

@Composable
fun CharacterDetailsScreen(
    details: CharacterDetails,
    modifier: Modifier = Modifier,
    viewModel: CharacterDetailsViewModel = hiltViewModel(),
) {
    Column(modifier = modifier.fillMaxSize()) {
        AsyncImage(
            modifier = Modifier.aspectRatio(1f),
            model = details.image,
            contentDescription = stringResource(R.string.character_image_content_description),
        )
        Column(modifier = Modifier.padding(16.dp)) {
            Text(details.name, fontSize = 30.sp)
            Text(details.species)
            Text(details.status)
            Text(details.originName)
            details.type.takeIf { it.isNotEmpty() }?.let { type ->
                Text(type)
            }
            Text(viewModel.formatDate(details.created))
        }
    }
}
