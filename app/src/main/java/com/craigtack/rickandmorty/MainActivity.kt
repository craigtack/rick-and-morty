package com.craigtack.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.craigtack.rickandmorty.details.CharacterDetailsScreen
import com.craigtack.rickandmorty.list.CharacterListScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@Serializable
object CharacterList

/**
 * Ideally pass only the ID and use a different API to request character details.
 */
@Serializable
data class CharacterDetails(
    val image: String,
    val name: String,
    val species: String,
    val status: String,
    val originName: String,
    val type: String,
    val created: String,
)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            MaterialTheme(
                colorScheme = lightColorScheme(
                    surface = Color(0xFF6650a4),
                    onSurface = Color.White,
                ),
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(stringResource(R.string.app_name)) },
                        )
                    }
                ) { innerPadding ->
                    NavHost(navController = navController, startDestination = CharacterList) {
                        composable<CharacterList> {
                            CharacterListScreen(modifier = Modifier.padding(innerPadding)) { character ->
                                navController.navigate(route =
                                    CharacterDetails(
                                        image = character.image,
                                        name = character.name,
                                        species = character.species,
                                        status = character.status,
                                        originName = character.originName,
                                        type = character.type,
                                        created = character.created,
                                    )
                                )
                            }
                        }
                        composable<CharacterDetails> { backStackEntry ->
                            val character: CharacterDetails = backStackEntry.toRoute()
                            CharacterDetailsScreen(modifier = Modifier.padding(innerPadding), details = character)
                        }
                    }
                }
            }
        }
    }
}
